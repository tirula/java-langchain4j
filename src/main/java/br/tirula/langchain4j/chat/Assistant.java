package br.tirula.langchain4j.chat;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface Assistant {
    String chat(String message);

    String chat(@MemoryId long memoryId, @UserMessage String userMessage);

}
