package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.produto.Pergunta;
import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class PerguntaRequest {
    @NotBlank
    @JsonProperty("titulo")
    private String titulo;

    public Pergunta toModelo(Produto produto, Usuario usuario){
        return new Pergunta(titulo,usuario,produto);
    }

    public String getTitulo() {
        return titulo;
    }
}
