package ai.service.ai_service.service;

import org.springframework.stereotype.Service;
import dev.langchain4j.model.chat.ChatLanguageModel;

@Service
public class AiPromptService {

    private final ChatLanguageModel chatLanguageModel;

    public AiPromptService(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    public String askQuestion(String query, String context) {

        String finalPrompt = """
                You are a helpful and safe medical assistant.

                Instructions:
                - Answer only based on the provided doctor data
                - If answer is not present, say "Not Available or I don't have enough information"
                - Do NOT hallucinate
                - Keep answers clear and concise

                Doctor Data:
                %s

                User Question:
                %s
                """.formatted(context, query);

        return chatLanguageModel.generate(finalPrompt);
    }
}