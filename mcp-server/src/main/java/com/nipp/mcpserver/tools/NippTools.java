package com.nipp.mcpserver.tools;

import java.time.Instant;
import java.util.Locale;

import org.springframework.ai.mcp.annotation.McpArg;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

/**
 * Example MCP tools exposed by this server. The annotation scanner picks these up
 * automatically (spring.ai.mcp.server.annotation-scanner.enabled defaults to true).
 */
@Component
public class NippTools {

	@McpTool(name = "nipp_echo", description = "Echoes the provided text back, prefixed with [echo].")
	public String echo(@McpArg(name = "text", description = "Text to echo back") String text) {
		if (text == null || text.isBlank()) {
			throw new IllegalArgumentException("text must not be blank");
		}
		return "[echo] " + text;
	}

	@McpTool(name = "nipp_time", description = "Returns the current server time in ISO-8601 UTC.")
	public String currentTime() {
		return Instant.now().toString();
	}

	@McpTool(name = "nipp_word_count", description = "Counts the words in the given text.")
	public int wordCount(@McpArg(name = "text", description = "Text to count words in") String text) {
		if (text == null || text.isBlank()) {
			return 0;
		}
		return text.trim().split("\\s+").length;
	}

	@McpTool(name = "nipp_uppercase", description = "Converts the given text to upper case.")
	public String toUpperCase(@McpArg(name = "text", description = "Text to convert") String text) {
		return (text == null ? "" : text).toUpperCase(Locale.ROOT);
	}

}
