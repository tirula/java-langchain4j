package br.tirula.langchain4j.ai;

import br.tirula.langchain4j.jpa.entity.MessageEntity;
import br.tirula.langchain4j.jpa.repository.MessageRepository;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

@Component
@RequiredArgsConstructor
@Transactional
public class JpaChatMemoryStore implements ChatMemoryStore {

    private final MessageRepository messageRepository;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        var entity = messageRepository.findByMemoryId(memoryId.toString());
        if(entity == null){
            return List.of();
        }
        return messagesFromJson(entity.getMessages());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        MessageEntity message = messageRepository.findByMemoryId(memoryId.toString());
        if (message == null) {
            var entity = MessageEntity.builder()
                    .memoryId(memoryId.toString())
                    .messages(messagesToJson(list))
                    .build();
            messageRepository.save(entity);
        } else {
            message.setMessages(messagesToJson(list));
            messageRepository.save(message);
        }
    }

    @Override
    public void deleteMessages(Object memoryId) {
        messageRepository.deleteByMemoryId(memoryId.toString());
    }
}
