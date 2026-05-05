package com.chatbot.controller;

import com.chatbot.model.ChatModels.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")   // allow frontend dev server
public class ChatController {

    private static final Logger log = Logger.getLogger(ChatController.class.getName());

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // ── Regular (non-streaming) endpoint ─────────────────────────────────────

    /**
     * POST /api/chat
     * Body: { "message": "Hello", "history": [...] }
     */
    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
        try {
            List<Message> messages = buildMessageHistory(request);

            String reply = chatClient.prompt()
                    .messages(messages)
                    .user(request.message())
                    .call()
                    .content();

            return ChatResponse.ok(reply);
        } catch (Exception e) {
            log.severe("Chat error: " + e.getMessage());
            return ChatResponse.fail("Something went wrong: " + e.getMessage());
        }
    }

    // ── Streaming endpoint ────────────────────────────────────────────────────

    /**
     * POST /api/chat/stream
     * Returns Server-Sent Events (text/event-stream).
     * Body: { "message": "Hello", "history": [...] }
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody ChatRequest request) {
        List<Message> messages = buildMessageHistory(request);

        return chatClient.prompt()
                .messages(messages)
                .user(request.message())
                .stream()
                .content()
                .onErrorReturn("[ERROR] Failed to get response. Check your API key.");
    }

    // ── Health check ──────────────────────────────────────────────────────────

    @GetMapping("/health")
    public String health() {
        return "Chatbot is running!";
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private List<Message> buildMessageHistory(ChatRequest request) {
        List<Message> messages = new ArrayList<>();

        if (request.history() != null) {
            for (MessageHistory h : request.history()) {
                if ("user".equalsIgnoreCase(h.role())) {
                    messages.add(new UserMessage(h.content()));
                } else if ("assistant".equalsIgnoreCase(h.role())) {
                    messages.add(new AssistantMessage(h.content()));
                }
            }
        }

        return messages;
    }
}
