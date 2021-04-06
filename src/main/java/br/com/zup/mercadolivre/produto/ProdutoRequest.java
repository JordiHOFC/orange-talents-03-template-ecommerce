package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.produto.caracteristicas.Caracteristica;
import br.com.zup.mercadolivre.produto.caracteristicas.CaracteristicaRequest;
import br.com.zup.mercadolivre.validator.ExistRegister;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoRequest {
    @NotBlank
    private String nome;
    @NotNull
    @Min(1)
    private BigDecimal valor;
    @Min(0)
    @NotNull
    private Integer quantidade;
    @Size(min = 3)
    @Valid
    List<CaracteristicaRequest>caracteristicas;

    @Length(min = 1000)
    private String descricao;

    @ExistRegister(domainClass = Categoria.class)
    private Long categoria;

    public ProdutoRequest(@NotBlank String nome, @NotNull @Min(1) BigDecimal valor, @Min(0) @NotNull Integer quantidade, @Size(min = 3) List<CaracteristicaRequest> caracteristicas, @Length(min = 1000) String descricao, Long categoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.categoria = categoria;
    }
    public Produto toModelo(){
        Categoria categorias=new Categoria(categoria);
        return new Produto(nome,valor,descricao,quantidade,categorias,caracteristicas.stream().map(CaracteristicaRequest::toModelo).collect(Collectors.toList()));
    }
}
