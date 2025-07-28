package br.tirula.langchain4j.chatstore;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    MessageEntity findByMemoryId(String memoryId);

    void deleteByMemoryId(String memoryId);
}
