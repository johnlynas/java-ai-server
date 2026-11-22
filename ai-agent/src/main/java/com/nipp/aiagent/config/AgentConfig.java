package com.nipp.aiagent.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wires the Spring AI ChatClient.
 *
 * <p>The ChatModel bean is only present when a provider API key is configured
 * (e.g. {@code spring.ai.openai.api-key}). When absent, the agent degrades to an
 * echo fallback so the service stays runnable in local/CI environments without keys.
 */
@Configuration
public class AgentConfig {

	@Bean
	public ChatClient chatClient(ChatModel chatModel) {
		return ChatClient.builder(chatModel)
				.defaultSystem("You are the Nipp AI agent. Be concise and helpful.")
				.build();
	}

}
