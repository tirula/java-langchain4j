package br.tirula.langchain4j.controller;

import br.tirula.langchain4j.ai.ChatStoreAgent;
import br.tirula.langchain4j.ai.JpaChatMemoryStore;
import br.tirula.langchain4j.jpa.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("chat-store")
@RestController
public class ChatStoreController {

    @Autowired
    private ChatStoreAgent chatStoreAgent;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping
    public ResponseEntity<?> enviarMensagem(@RequestBody String mensagem,
                                            @RequestHeader("userId") String userId){
        var reuslt = this.chatStoreAgent.enviarMensagem(userId, mensagem);
        return ResponseEntity.ok(reuslt);
    }

    @GetMapping
    public ResponseEntity<?> listarTodos(@RequestHeader("userId") String userId){
        var reuslt = this.messageRepository.findByMemoryId(userId);
        return ResponseEntity.ok(reuslt.getMessages());
    }
}



