package br.com.zup.mercadolivre.pagamento;

import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class NotaFiscal {

    @Id @Column(nullable = false, unique = true)
    private String numero;

    @OneToOne
    private Usuario comprador;

    @OneToOne
    private Compra compra;

    public NotaFiscal(Usuario comprador, Compra compra) {
        this.numero= UUID.randomUUID().toString();
        this.comprador = comprador;
        this.compra = compra;
    }
    @Deprecated
    public NotaFiscal() {
    }

    public String getNumero() {
        return numero;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Compra getCompra() {
        return compra;
    }
}
