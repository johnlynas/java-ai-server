package com.nipp.shared.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChatResponseTest {

	@Test
	@DisplayName("of() maps all fields")
	void ofMapsAllFields() {
		ChatResponse res = ChatResponse.of("u1", "pong", 42L);
		assertEquals("u1", res.userId());
		assertEquals("pong", res.reply());
		assertEquals(42L, res.durationMillis());
	}

	@Test
	@DisplayName("components are exposed directly")
	void componentsExposed() {
		ChatResponse res = new ChatResponse(null, "ok", 0L);
		assertEquals(null, res.userId());
		assertEquals("ok", res.reply());
		assertEquals(0L, res.durationMillis());
	}

}
