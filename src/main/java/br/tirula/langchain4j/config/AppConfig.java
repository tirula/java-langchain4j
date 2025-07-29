package br.tirula.langchain4j.config;

import br.tirula.langchain4j.ai.ChatStoreAgent;
import br.tirula.langchain4j.ai.JpaChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

@Configuration
public class AppConfig {
    @Value("${app.openai.api-key}")
    private String apiKey;

    @Value("${app.rag-path}")
    private String ragPath;

    @Bean
    public ChatModel chatModel() {
        return  OpenAiChatModel.builder()
                .apiKey(this.apiKey)
                .modelName(GPT_4_O_MINI)
                .build();
    }
    @Bean
    ChatMemoryProvider chatMemoryProvider(JpaChatMemoryStore jpaChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .chatMemoryStore(jpaChatMemoryStore)
                .maxMessages(10)
                .build();
    }


    @Bean
    ChatStoreAgent chatStoreAgent(ChatModel chatModel,
                                  ChatMemoryProvider chatMemoryProvider) {
        return AiServices.builder(ChatStoreAgent.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }

}
