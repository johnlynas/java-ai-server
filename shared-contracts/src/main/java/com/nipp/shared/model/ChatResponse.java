package com.nipp.shared.model;

/**
 * Shared response DTO for chat interactions.
 */
public record ChatResponse(
        String userId,
        String reply,
        long durationMillis
) {
    public static ChatResponse of(String userId, String reply, long durationMillis) {
        return new ChatResponse(userId, reply, durationMillis);
    }
}
