package com.nipp.aiagent.api;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nipp.aiagent.service.ChatService;
import com.nipp.shared.enums.ServiceName;
import com.nipp.shared.model.ChatRequest;
import com.nipp.shared.model.ChatResponse;

@RestController
@RequestMapping("/api/v1")
public class AgentController {

	private final ChatService chatService;

	public AgentController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping("/health")
	public HealthDto health() {
		return new HealthDto(ServiceName.AI_AGENT.wireName(), "UP", chatService.hasLiveModel());
	}

	@PostMapping("/chat")
	public ChatResponse chat(@RequestBody ChatRequest request) {
		long start = System.nanoTime();
		String reply = chatService.chat(request.userId(), request.message());
		return ChatResponse.of(request.userId(), reply, Duration.ofNanos(System.nanoTime() - start).toMillis());
	}

	public record HealthDto(String service, String status, boolean liveModel) {
	}

}
