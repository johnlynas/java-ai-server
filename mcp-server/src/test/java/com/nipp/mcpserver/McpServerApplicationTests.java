package com.nipp.mcpserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.nipp.mcpserver.tools.NippTools;

@SpringBootTest
@AutoConfigureMockMvc
class McpServerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private NippTools nippTools;

	@Test
	@DisplayName("context loads")
	void contextLoads() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("GET /api/v1/health identifies mcp-server")
	void healthEndpoint() throws Exception {
		mockMvc.perform(get("/api/v1/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.service").value("mcp-server"))
				.andExpect(jsonPath("$.status").value("UP"));
	}

	@Test
	@DisplayName("tool: echo prefixes text")
	void toolEcho() {
		assertThat(nippTools.echo("hi")).isEqualTo("[echo] hi");
	}

	@Test
	@DisplayName("tool: wordCount counts whitespace-separated words")
	void toolWordCount() {
		assertThat(nippTools.wordCount("one two three")).isEqualTo(3);
		assertThat(nippTools.wordCount("  padded   text ")).isEqualTo(2);
		assertThat(nippTools.wordCount("")).isZero();
		assertThat(nippTools.wordCount(null)).isZero();
	}

	@Test
	@DisplayName("tool: toUpperCase converts and tolerates null")
	void toolUpperCase() {
		assertThat(nippTools.toUpperCase("abc 123")).isEqualTo("ABC 123");
		assertThat(nippTools.toUpperCase(null)).isEmpty();
	}

	@Test
	@DisplayName("tool: currentTime returns ISO-8601 instant")
	void toolCurrentTime() {
		String time = nippTools.currentTime();
		assertThat(time).isNotBlank();
		org.junit.jupiter.api.Assertions.assertDoesNotThrow(
				() -> java.time.Instant.parse(time));
	}

}
