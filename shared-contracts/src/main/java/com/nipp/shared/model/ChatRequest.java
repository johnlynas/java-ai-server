package com.nipp.shared.model;

/**
 * Shared request DTO for chat interactions between app-server, ai-agent and mcp-server.
 */
public record ChatRequest(
        String userId,
        String message
) {
    public ChatRequest {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("message must not be blank");
        }
    }

    public static ChatRequest of(String message) {
        return new ChatRequest(null, message);
    }
}
