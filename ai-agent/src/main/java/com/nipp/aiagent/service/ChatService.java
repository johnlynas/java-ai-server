package com.nipp.aiagent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/**
 * Chat orchestration: uses the Spring AI ChatClient when a model provider is
 * configured, otherwise falls back to an echo reply so the service is always
 * runnable (local dev / CI without API keys).
 */
@Service
public class ChatService {

	private static final Logger log = LoggerFactory.getLogger(ChatService.class);

	private final ObjectProvider<ChatClient> chatClientProvider;

	public ChatService(ObjectProvider<ChatClient> chatClientProvider) {
		this.chatClientProvider = chatClientProvider;
	}

	public boolean hasLiveModel() {
		return chatClientProvider.getIfAvailable() != null;
	}

	public String chat(String userId, String message) {
		ChatClient client = chatClientProvider.getIfAvailable();
		if (client == null) {
			log.warn("No ChatModel configured - returning echo fallback");
			return "[echo] " + message;
		}
		try {
			String reply = client.prompt()
					.user(message)
					.call()
					.content();
			return reply != null ? reply : "";
		} catch (Exception ex) {
			log.error("ChatModel call failed, falling back to echo: {}", ex.getMessage());
			return "[echo] " + message;
		}
	}

}
