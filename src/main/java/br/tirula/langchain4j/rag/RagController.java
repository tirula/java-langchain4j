package br.tirula.langchain4j.rag;

import br.tirula.langchain4j.chat.Assistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("rag")
@RestController
public class RagController {

    private Assistant ragAssistant;

    @Autowired
    @Qualifier("assistant")
    private Assistant assistant;

    @GetMapping
    public ResponseEntity<?> consultarRag(@RequestParam("pergunta") String pergunta){
        String answer = ragAssistant.chat(pergunta);
        return ResponseEntity.ok(answer);
    }
}
