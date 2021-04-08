package br.com.zup.mercadolivre.produto.caracteristicas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaracteristicasResponse {
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("descricao")
    private String descricao;

    public CaracteristicasResponse(Caracteristica caracteristica) {
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }


}
