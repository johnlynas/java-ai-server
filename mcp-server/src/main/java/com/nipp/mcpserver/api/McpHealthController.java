package com.nipp.mcpserver.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nipp.shared.enums.ServiceName;

@RestController
public class McpHealthController {

	@GetMapping("/api/v1/health")
	public HealthDto health() {
		return new HealthDto(ServiceName.MCP_SERVER.wireName(), "UP");
	}

	public record HealthDto(String service, String status) {
	}

}
