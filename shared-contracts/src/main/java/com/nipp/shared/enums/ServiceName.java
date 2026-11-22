package com.nipp.shared.enums;

/**
 * Identifies the deployable services in this monorepo.
 */
public enum ServiceName {
    APP_SERVER("app-server"),
    AI_AGENT("ai-agent"),
    MCP_SERVER("mcp-server");

    private final String wireName;

    ServiceName(String wireName) {
        this.wireName = wireName;
    }

    public String wireName() {
        return wireName;
    }

    public static ServiceName fromWireName(String name) {
        for (ServiceName s : values()) {
            if (s.wireName.equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown service: " + name);
    }
}
