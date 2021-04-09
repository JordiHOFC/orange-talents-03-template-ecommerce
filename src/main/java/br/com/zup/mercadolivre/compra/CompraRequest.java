package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.validator.ValidateEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {
    @Positive
    @NotNull
    @JsonProperty("quantidade")
    private Integer quantidade;

    @ValidateEnum(targetClassType = MetodoPagamento.class)
    @JsonProperty("metodoPagamento")
    private String metodoPagamento;

    public Compra toModelo(Usuario comprador, Produto produto){
        MetodoPagamento pagamento=MetodoPagamento.valueOf(metodoPagamento);
        return new Compra(comprador,produto,produto.getValor(),quantidade,pagamento);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getMetodoPagamento() {
        return MetodoPagamento.valueOf(metodoPagamento).getGatway();
    }
}
