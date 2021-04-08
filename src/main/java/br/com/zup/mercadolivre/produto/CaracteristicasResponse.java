package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.produto.Caracteristica;
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
