package com.nipp.appserver;

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

@SpringBootTest
@AutoConfigureMockMvc
class AppServerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("context loads")
	void contextLoads() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("GET /api/v1/health returns app-server UP")
	void healthEndpoint() throws Exception {
		mockMvc.perform(get("/api/v1/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.service").value("app-server"))
				.andExpect(jsonPath("$.status").value("UP"));
	}

	@Test
	@DisplayName("POST /api/v1/chat persists message and returns it with an id")
	void chatPersistsMessage() throws Exception {
		mockMvc.perform(post("/api/v1/chat")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\":\"t-user\",\"message\":\"hello from junit\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.userId").value("t-user"))
				.andExpect(jsonPath("$.content").value("hello from junit"));

		mockMvc.perform(get("/api/v1/chat/t-user"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].content").value("hello from junit"));
	}

	@Test
	@DisplayName("POST /api/v1/chat with blank userId defaults to anonymous")
	void chatDefaultsAnonymousUser() throws Exception {
		mockMvc.perform(post("/api/v1/chat")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"message\":\"anon msg\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.userId").value("anonymous"));
	}

}
