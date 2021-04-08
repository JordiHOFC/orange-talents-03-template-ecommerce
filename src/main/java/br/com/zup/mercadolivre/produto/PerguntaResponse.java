package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.produto.Pergunta;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PerguntaResponse {
    @JsonProperty("titulo")
    private String titulo;

    public PerguntaResponse(Pergunta pergunta){
        this.titulo=pergunta.getTitulo();
    }
}
