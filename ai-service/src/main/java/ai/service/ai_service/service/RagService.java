package ai.service.ai_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.langchain4j.data.segment.TextSegment;

@Service
public class RagService {

    private final EmbeddingService embeddingService;
    private final AiPromptService aiPromptService;

    public RagService(EmbeddingService embeddingService,
                      AiPromptService aiPromptService) {
        this.embeddingService = embeddingService;
        this.aiPromptService = aiPromptService;
    }

    public String searchAndAnswer(String query) {

        // 🔍 Step 1: Retrieve relevant chunks
        List<TextSegment> results = embeddingService.search(query);

        if (results.isEmpty()) {
            return "No relevant data found.";
        }

        // 🧠 Step 2: Build context (clean + limited)
        String context = results.stream()
                .map(TextSegment::text)
                .collect(Collectors.joining("\n\n"));

        // 🤖 Step 3: Ask LLM
        return aiPromptService.askQuestion(query, context);
    }
}