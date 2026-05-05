package com.chatbot.model;

import java.util.List;

public class ChatModels {

    // ── Request ──────────────────────────────────────────────────────────────

    public record ChatRequest(
            String message,
            List<MessageHistory> history
    ) {}

    public record MessageHistory(
            String role,   // "user" or "assistant"
            String content
    ) {}

    // ── Response ─────────────────────────────────────────────────────────────

    public record ChatResponse(
            String reply,
            boolean success,
            String error
    ) {
        public static ChatResponse ok(String reply) {
            return new ChatResponse(reply, true, null);
        }

        public static ChatResponse fail(String error) {
            return new ChatResponse(null, false, error);
        }
    }
}
