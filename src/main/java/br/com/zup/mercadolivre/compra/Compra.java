package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.pagamento.Pagamento;
import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;


import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Compra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario comprador;
    @ManyToOne
    private Produto produto;
    @Positive
    private BigDecimal valor;
    @Positive
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private Status status=Status.INICIADA;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;


    public Compra(Usuario comprador, Produto produto, @Positive BigDecimal valor, @Positive Integer quantidade, MetodoPagamento metodoPagamento) {
        this.comprador = comprador;
        this.produto = produto;
        this.valor = valor;
        this.quantidade = quantidade;
        this.metodoPagamento = metodoPagamento;
    }
    @Deprecated
    public Compra() {
    }

    public Long getId() {
        return id;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Produto getProduto() {
        return produto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }


    public void finalizarCompra(Status sucesso) {
        this.status=sucesso;
    }
}
