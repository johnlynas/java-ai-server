package com.nipp.shared.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChatRequestTest {

	@Nested
	@DisplayName("construction")
	class Construction {

		@Test
		@DisplayName("valid request exposes all components")
		void validRequestExposesComponents() {
			ChatRequest req = new ChatRequest("u1", "hello");
			assertEquals("u1", req.userId());
			assertEquals("hello", req.message());
		}

		@Test
		@DisplayName("null userId is allowed")
		void nullUserIdAllowed() {
			ChatRequest req = new ChatRequest(null, "hi");
			assertEquals(null, req.userId());
			assertEquals("hi", req.message());
		}

		@Test
		@DisplayName("null message is rejected")
		void nullMessageRejected() {
			assertThrows(IllegalArgumentException.class, () -> new ChatRequest("u1", null));
		}

		@Test
		@DisplayName("blank message is rejected")
		void blankMessageRejected() {
			assertThrows(IllegalArgumentException.class, () -> new ChatRequest("u1", "   "));
		}
	}

	@Nested
	@DisplayName("factory")
	class Factory {

		@Test
		@DisplayName("of(message) produces anonymous request")
		void ofProducesAnonymousRequest() {
			ChatRequest req = ChatRequest.of("ping");
			assertEquals(null, req.userId());
			assertEquals("ping", req.message());
		}
	}

	@Test
	@DisplayName("records value-based equality")
	void recordEquality() {
		ChatRequest a = new ChatRequest("u1", "hello");
		ChatRequest b = new ChatRequest("u1", "hello");
		assertEquals(a, b);
		assertSame(a, a);
	}

}
