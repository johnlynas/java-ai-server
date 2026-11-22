package com.nipp.appserver.api;

import java.time.Instant;
import java.util.List;

public record ChatMessageDto(Long id, String userId, String content, Instant createdAt) {

	public static ChatMessageDto from(com.nipp.appserver.domain.ChatMessage m) {
		return new ChatMessageDto(m.getId(), m.getUserId(), m.getContent(), m.getCreatedAt());
	}

	public static List<ChatMessageDto> fromAll(List<com.nipp.appserver.domain.ChatMessage> messages) {
		return messages.stream().map(ChatMessageDto::from).toList();
	}

}
