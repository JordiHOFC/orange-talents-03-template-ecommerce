package br.com.zup.mercadolivre.produto.caracteristicas;

import br.com.zup.mercadolivre.produto.caracteristicas.Caracteristica;

import javax.validation.constraints.NotBlank;

public class CaracteristicaRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;

    public CaracteristicaRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Caracteristica toModelo(){
        return new Caracteristica(nome,descricao);
    }
}
