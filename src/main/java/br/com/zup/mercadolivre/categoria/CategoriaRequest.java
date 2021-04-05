package br.com.zup.mercadolivre.categoria;

import br.com.zup.mercadolivre.validator.UniqueValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class CategoriaRequest {
    @UniqueValue(domainClass = Categoria.class,field = "nome")
    @NotBlank
    @JsonProperty("nome")
    private String nome;
    @Valid
    @JsonProperty("categoriaMae")
    private CategoriaMaeRequest categoriaMae;

    public Categoria toModelo(){
        if(categoriaMae==null){
            return new Categoria(nome);
        }
        return new Categoria(nome, categoriaMae.toModelo());
    }

    public CategoriaMaeRequest getCategoriaMae() {
        return categoriaMae;
    }
}
