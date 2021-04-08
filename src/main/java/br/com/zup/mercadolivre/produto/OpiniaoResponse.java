package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.produto.Opiniao;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OpiniaoResponse {
    @JsonProperty("titulo")
    private String titulo;
    @JsonProperty("descricao")
    private String descricao;

    public OpiniaoResponse(Opiniao opiniao) {
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
    }
}
