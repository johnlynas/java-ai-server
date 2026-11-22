package com.nipp.shared.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceNameTest {

	@Test
	@DisplayName("each service has its wire name")
	void wireNames() {
		assertEquals("app-server", ServiceName.APP_SERVER.wireName());
		assertEquals("ai-agent", ServiceName.AI_AGENT.wireName());
		assertEquals("mcp-server", ServiceName.MCP_SERVER.wireName());
	}

	@Test
	@DisplayName("fromWireName round-trips (case-insensitive)")
	void fromWireNameRoundTrips() {
		for (ServiceName s : ServiceName.values()) {
			assertEquals(s, ServiceName.fromWireName(s.wireName()));
			assertEquals(s, ServiceName.fromWireName(s.wireName().toUpperCase()));
		}
	}

	@Test
	@DisplayName("unknown service name is rejected")
	void unknownServiceRejected() {
		assertThrows(IllegalArgumentException.class, () -> ServiceName.fromWireName("nope"));
	}

}
