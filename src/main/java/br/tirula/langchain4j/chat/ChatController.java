package br.tirula.langchain4j.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("chat")
@RestController
public class ChatController {

    @Autowired
    @Qualifier("assistant")
    private Assistant assistant;

    @PostMapping
    public ResponseEntity<?> enviarMensagem(@RequestBody String mensagem,
                                            @RequestHeader("userId") Long userId){
        String answer = assistant.chat(userId,mensagem);
        return ResponseEntity.ok(answer);
    }
}
