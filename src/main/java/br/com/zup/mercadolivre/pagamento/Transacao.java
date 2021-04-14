package br.com.zup.mercadolivre.pagamento;

import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.compra.Status;
import br.com.zup.mercadolivre.pagamento.Pagamento;

import javax.persistence.*;

@Entity
public class Transacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @ManyToOne
    private Pagamento pagamento;
    @ManyToOne
    private Compra compra;

    public Transacao(Status status, Pagamento pagamento, Compra compra) {
        this.status = status;
        this.pagamento = pagamento;
        this.compra = compra;
    }

    @Deprecated
    public Transacao() {
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }
}
