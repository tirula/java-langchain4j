package br.tirula.langchain4j.chatstore;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ChatStoreAgent {

    @SystemMessage("""
            Voce é especializado em conversar sobre temas holisticos
            """)
    String enviarMensagem(@MemoryId String memoryId, @UserMessage String userMessage);

}