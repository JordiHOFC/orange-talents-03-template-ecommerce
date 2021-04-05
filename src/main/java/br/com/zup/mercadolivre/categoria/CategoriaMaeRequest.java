package br.com.zup.mercadolivre.categoria;

import br.com.zup.mercadolivre.validator.ExistRegister;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CategoriaMaeRequest {
    @NotNull
    @JsonProperty("id")
    @ExistRegister(domainClass=Categoria.class)
    private Long id;

    public CategoriaMaeRequest(Long id){
        this.id=id;
    }

    public CategoriaMaeRequest() {
    }
    

    public Long getId() {
        return id;
    }

    public Categoria toModelo(){
        return new Categoria(id);
    }
}
