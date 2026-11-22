package com.nipp.aiagent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
		// Keep tests hermetic: no live MCP server, no real API key.
		"spring.ai.mcp.client.enabled=false",
		"spring.ai.openai.api-key="
})
@AutoConfigureMockMvc
class AiAgentApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("context loads")
	void contextLoads() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("GET /api/v1/health identifies ai-agent")
	void healthEndpoint() throws Exception {
		mockMvc.perform(get("/api/v1/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.service").value("ai-agent"))
				.andExpect(jsonPath("$.status").value("UP"))
				.andExpect(jsonPath("$.liveModel").isBoolean());
	}

	@Test
	@DisplayName("POST /api/v1/chat returns a reply with timing")
	void chatReturnsReply() throws Exception {
		mockMvc.perform(post("/api/v1/chat")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\":\"t-user\",\"message\":\"hello agent\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userId").value("t-user"))
				.andExpect(jsonPath("$.reply").isNotEmpty())
				.andExpect(jsonPath("$.durationMillis").isNumber());
	}

	@Test
	@DisplayName("POST /api/v1/chat rejects blank message (shared contract)")
	void chatRejectsBlankMessage() throws Exception {
		mockMvc.perform(post("/api/v1/chat")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"message\":\"  \"}"))
				.andExpect(status().isBadRequest());
	}

}
