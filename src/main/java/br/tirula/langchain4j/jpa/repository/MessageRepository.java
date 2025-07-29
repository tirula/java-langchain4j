package br.tirula.langchain4j.jpa.repository;


import br.tirula.langchain4j.jpa.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    MessageEntity findByMemoryId(String memoryId);

    void deleteByMemoryId(String memoryId);
}
