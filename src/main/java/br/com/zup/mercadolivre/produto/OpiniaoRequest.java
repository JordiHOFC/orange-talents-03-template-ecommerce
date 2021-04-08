package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.produto.Opiniao;
import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.validator.ExistRegister;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpiniaoRequest {
    @NotBlank
    private String titulo;

    @NotBlank @Length(max = 500)
    private String descricao;

    @Min(1) @Max(5)
    private Integer nota;


    public OpiniaoRequest(@NotBlank String titulo, @NotBlank @Length(max = 500) String descricao, @Min(1) @Max(5) Integer nota, @NotNull Long produto) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.nota = nota;

    }
    public Opiniao toModelo(@ExistRegister(domainClass = Produto.class) Produto produto, Usuario usuario){
        return new Opiniao(titulo,descricao,nota,produto,usuario);
    }
}
