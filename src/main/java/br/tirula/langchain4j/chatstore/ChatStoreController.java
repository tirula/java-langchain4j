package br.tirula.langchain4j.chatstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("chat-store")
@RestController
public class ChatStoreController {

    @Autowired
    private ChatStoreAgent chatStoreAgent;

    @PostMapping
    public ResponseEntity<?> enviarMensagem(@RequestBody String mensagem,
                                            @RequestHeader("userId") String userId){
        var reuslt = this.chatStoreAgent.enviarMensagem(userId, mensagem);
        return ResponseEntity.ok(reuslt);
    }}
