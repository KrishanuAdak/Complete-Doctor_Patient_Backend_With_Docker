package ai.service.ai_service.config;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ai.service.ai_service.ToolCalling.AppointmentAgent;
import ai.service.ai_service.ToolCalling.AppointmentTool;
import ai.service.ai_service.ToolCalling.DoctorTool;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AiConfig {

    @Value("${spring.ai.openai.api-key}")
    public String apiKey;

    @Value("${rag.ingest-on-startup:false}")
    public boolean ingestOnStartup;

    @Value("${rag.source-path:rag.pdf}")
    private String sourcePath;

    @Bean
    public CommandLineRunner ingestDocuments(EmbeddingStore<TextSegment> embeddingStore,
                                              EmbeddingModel embeddingModel) {
        return args -> {
            if (!ingestOnStartup) {
                log.info("RAG ingestion skipped (rag.ingest-on-startup=false)");
                return;
            }

            log.info("Starting RAG ingestion from classpath: {}", sourcePath);

            Resource resource = new ClassPathResource(sourcePath);
            DocumentParser parser = new ApacheTikaDocumentParser();

            Document document;
            try (InputStream inputStream = resource.getInputStream()) {
                document = parser.parse(inputStream);
            }

            DocumentSplitter splitter = DocumentSplitters.recursive(500, 50);
            List<TextSegment> segments = splitter.split(document);

            Response<List<Embedding>> embeddings = embeddingModel.embedAll(segments);
            embeddingStore.addAll(embeddings.content(), segments);

            log.info("Ingested {} segments from {} into ChromaDB", segments.size(), sourcePath);
        };
    }

    @Bean
    public ContentRetriever embeddingRetriever(EmbeddingStore<TextSegment> embeddingStore,
                                                EmbeddingModel embeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(6)
                .minScore(0.6)
                .build();
    }

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o")
                .temperature(0.2)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    public AppointmentAgent appointmentAgent(DoctorTool doctorTool, AppointmentTool appointmentTools,
                                              ChatLanguageModel chatLanguageModel, ContentRetriever retriever) {
        return AiServices.builder(AppointmentAgent.class)
                .contentRetriever(retriever)
                .chatLanguageModel(chatLanguageModel)
                .tools(appointmentTools, doctorTool)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(12))
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-3-small")
                .build();
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return ChromaEmbeddingStore.builder()
               // .baseUrl("http://localhost:8000")
               // .apiVersion()
               .baseUrl("http://chromadb:8000")
                .collectionName("appointment-easy-rag")
                .build();
    }
}