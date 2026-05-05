package com.chatbot.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    private static final String SYSTEM_PROMPT = """
            You are a helpful, friendly, and knowledgeable AI assistant.
            You provide clear, concise, and accurate answers.
            If you don't know something, say so honestly.
            Keep responses conversational but informative.
            Format code blocks with proper markdown when sharing code.
            """;

    /**
     * Creates a ChatClient bean with a default system prompt.
     * The OpenAI client is configured to point to Groq's API via application.properties.
     */
    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .build();
    }
}
