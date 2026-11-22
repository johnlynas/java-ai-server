package com.nipp.appserver.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nipp.appserver.domain.ChatMessage;
import com.nipp.appserver.domain.ChatMessageRepository;
import com.nipp.shared.enums.ServiceName;
import com.nipp.shared.model.ChatRequest;

@RestController
@RequestMapping("/api/v1")
public class ChatController {

	private final ChatMessageRepository repository;

	public ChatController(ChatMessageRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/health")
	public HealthDto health() {
		return new HealthDto(ServiceName.APP_SERVER.wireName(), "UP");
	}

	@PostMapping("/chat")
	@ResponseStatus(HttpStatus.CREATED)
	public ChatMessageDto saveChat(@RequestBody ChatRequest request) {
		String userId = (request.userId() == null || request.userId().isBlank()) ? "anonymous" : request.userId();
		return ChatMessageDto.from(repository.save(new ChatMessage(userId, request.message())));
	}

	@GetMapping("/chat/{userId}")
	public List<ChatMessageDto> history(@PathVariable String userId) {
		return ChatMessageDto.fromAll(repository.findByUserIdOrderByCreatedAtAsc(userId));
	}

	public record HealthDto(String service, String status) {
	}

}
