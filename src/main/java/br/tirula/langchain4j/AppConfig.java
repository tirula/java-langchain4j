package br.tirula.langchain4j;

import br.tirula.langchain4j.chat.Assistant;
import br.tirula.langchain4j.chatstore.ChatStoreAgent;
import br.tirula.langchain4j.chatstore.JpaChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

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
    public StreamingChatModel streamingChatModel(){
        return OpenAiStreamingChatModel.builder()
                .apiKey(this.apiKey)
                .modelName(GPT_4_O_MINI)
                .build();
    }


    @Qualifier("ragAssistant")
    public Assistant ragAssistant(){
        List<Document> documents = FileSystemDocumentLoader
                .loadDocuments(ragPath);
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        return AiServices.builder(Assistant.class)
                .chatModel(chatModel())
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();
    }


    @Bean
    @Qualifier("assistant")
    public Assistant assistant(){
        return AiServices.builder(Assistant.class)
                .chatModel(chatModel())
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
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
