# java-ai-server

Spring AI monorepo: shared contracts + three deployable services, built with Maven on Java 21 (Spring Boot 4.2.0, Spring AI 2.1.0).

```
java-ai-server/
├── pom.xml                  # Root parent (pom packaging) - imports spring-ai-bom, manages shared-contracts version
├── shared-contracts/        # Plain Java JAR: DTOs, enums, API models (NO spring-boot-maven-plugin)
│   ├── pom.xml
│   └── Dockerfile
├── app-server/              # Spring Boot REST API + H2 in-memory DB  -> port 8080
│   ├── pom.xml
│   └── Dockerfile
├── ai-agent/                # Spring AI ChatClient + MCP client       -> port 8081
│   ├── pom.xml
│   └── Dockerfile
└── mcp-server/              # Spring AI MCP server, @McpTool beans    -> port 8082
    ├── pom.xml
    └── Dockerfile
```

## Requirements
- JDK 21 (e.g. Amazon Corretto 21)
- Maven 3.9+

## Build
```bash
mvn clean package
```
Builds all four modules in reactor order (shared-contracts first, so the apps resolve it from the local workspace).

## Run
```bash
# one service at a time:
mvn spring-boot:run -pl app-server
mvn spring-boot:run -pl ai-agent
mvn spring-boot:run -pl mcp-server

# or run the packaged jars (all three simultaneously):
java -jar app-server/target/app-server-1.0.0-SNAPSHOT.jar
java -jar ai-agent/target/ai-agent-1.0.0-SNAPSHOT.jar
java -jar mcp-server/target/mcp-server-1.0.0-SNAPSHOT.jar
```

## Endpoints
| Service    | Port | Health                          | Notes |
|------------|------|---------------------------------|-------|
| app-server | 8080 | `GET /api/v1/health`            | `POST /api/v1/chat`, `GET /api/v1/chat/{userId}` (H2), H2 console at `/h2-console` |
| ai-agent   | 8081 | `GET /api/v1/health`            | `POST /api/v1/chat` - uses local Ollama ChatClient (default model llama3.2); echo fallback if Ollama is unreachable; MCP client connects to mcp-server |
| mcp-server | 8082 | `GET /api/v1/health`            | Streamable-HTTP MCP endpoint at `/mcp`; tools: `nipp_echo`, `nipp_time`, `nipp_word_count`, `nipp_uppercase` |

## Configuration (env vars)
- `OLLAMA_BASE_URL` - Ollama endpoint for ai-agent (default `http://localhost:11434`)
- `OLLAMA_MODEL` - chat model name (default `llama3.2`)
- `OLLAMA_PULL_STRATEGY` - `never`, `when_missing` (default), or `always`
- `MCP_SERVER_URL` - where ai-agent's MCP client connects (default `http://localhost:8082`)
- `MCP_CLIENT_ENABLED` - set `false` to run ai-agent without a live mcp-server

## Ollama setup (ai-agent)
```bash
ollama pull llama3.2        # model used by default
# then start ai-agent; it connects to http://localhost:11434 automatically
```
The pre-switch OpenAI configuration is backed up in `backup/openai-20261122/` (see RESTORE.md there).

## Docker
Each module has its own multi-stage Dockerfile, so CI can build/deploy one service
without rebuilding the others:
```bash
docker build -f app-server/Dockerfile --build-arg BASE_DIR=. -t nipp/app-server .
docker run -p 8080:8080 nipp/app-server
```
