package ai.service.ai_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.service.ai_service.ToolCalling.AppointmentAgent;
import ai.service.ai_service.ToolCalling.AppointmentTool;
import ai.service.ai_service.ToolCalling.DoctorTool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

@Configuration
public class AiConfig {
  @Value("${spring.ai.openai.api-key}")
  public String apiKey;

  @Bean
  public ChatLanguageModel chatLanguageModel() {
    return OpenAiChatModel.builder()
        .apiKey(apiKey)
        .modelName("gpt-4o")
        .temperature(0.2)
        .build();
  }

  @Bean
  public AppointmentAgent appointmentAgent(DoctorTool doctorTools, AppointmentTool appointmentTools,
      ChatLanguageModel chatLanguageModel) {
    System.out.println(appointmentTools + " " + chatLanguageModel + "registered");
    return AiServices.builder(AppointmentAgent.class)
        .chatLanguageModel(chatLanguageModel)
        .tools(appointmentTools, doctorTools)
        .chatMemory(MessageWindowChatMemory.withMaxMessages(12))
        .build();
  }
}

// @Bean
// public EmbeddingModel embeddingModel() {
// return OpenAiEmbeddingModel.builder()
// .apiKey(apiKey)
// .modelName("text-embedding-3-small")
// .build();
// }
// @Bean
// public EmbeddingStore<TextSegment> embeddingStore() {

// return ChromaEmbeddingStore.builder()
// .baseUrl("http://localhost:8000")
// .collectionName("doctor-ai")
// //.embeddingModel(embeddingModel())
// .build();
// }

// EmbeddingStore<TextSegment> embeddingStore(){
// return ChromaEmbeddingStore.builder().
// baseUrl("http://localhost:1000")
// .collectionName("doctor-ai").embeddingModel(embeddingModel())
// .build();
// }
