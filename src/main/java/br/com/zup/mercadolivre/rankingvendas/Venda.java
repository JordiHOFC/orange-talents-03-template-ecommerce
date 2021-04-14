package br.com.zup.mercadolivre.rankingvendas;

import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.persistence.*;

@Entity
public class Venda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario vendedor;
    @ManyToOne
    private Compra compra;

    public Venda(Usuario vendedor, Compra compra) {
        this.vendedor = vendedor;
        this.compra = compra;
    }

    public Long getId() {
        return id;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public Compra getCompra() {
        return compra;
    }
}
