package ai.service.ai_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;

@Configuration
public class AiConfig {
    @Value("${open-ai.api.key}")
    public String apiKey;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini")
                .temperature(0.2)
                .build();
    }
    @Bean
    public EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-3-small")
                .build();
    }
    // @Bean
    // public EmbeddingStore<TextSegment> embeddingStore() {
    //     return new InMemoryEmbeddingStore<>();
    // }
      //  @Bean
    // public EmbeddingStore<TextSegment> embeddingStore() {

    //     return ChromaEmbeddingStore.builder()
    //             .baseUrl("http://chromadb:1000")
    //             .collectionName("doctor-ai")
    //            // .embeddingModel(embeddingModel())
    //             .build();
    // }

    // EmbeddingStore<TextSegment> embeddingStore(){
    //     return ChromaEmbeddingStore.builder().
    //     baseUrl("http://localhost:1000")
    //     .collectionName("doctor-ai").embeddingModel(embeddingModel())
    //     .build();
    // }

   
}
