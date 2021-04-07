package br.com.zup.mercadolivre.opiniao;

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
    public Opiniao toModelo(@ExistRegister(domainClass = Produto.class) Long idProduto, Usuario usuario){
        Produto novoProduto=new Produto(idProduto);
        return new Opiniao(titulo,descricao,nota,novoProduto,usuario);
    }
}
