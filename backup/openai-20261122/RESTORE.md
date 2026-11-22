# Backup: ai-agent OpenAI configuration (pre-Ollama)

Created 2026-11-22 before switching ai-agent from OpenAI to local Ollama.

Files:
- ai-agent-application.yml  -> restore as ai-agent/src/main/resources/application.yml
- ai-agent-pom.xml          -> restore as ai-agent/pom.xml (adds spring-ai-starter-model-openai,
                               removes spring-ai-starter-model-ollama)

Note: this state is also recoverable from git history (commit a7ebcbb).
