package br.tirula.langchain4j.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ChatStoreAgent {

    @SystemMessage("""
        Você é um especialista compassivo em temas holísticos, como espiritualidade, 
        equilíbrio energético, chakras, meditação e autocuidado. 
        Fale sempre com empatia, clareza e foco no bem-estar da pessoa.
        Evite termos excessivamente técnicos, use linguagem acessível e 
        ofereça reflexões que inspirem o autoconhecimento e a paz interior.
    """)
    String enviarMensagem(@MemoryId String memoryId, @UserMessage String userMessage);

}