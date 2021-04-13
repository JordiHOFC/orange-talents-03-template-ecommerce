package br.com.zup.mercadolivre.pagamento;

import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.compra.Status;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Pagamento {
    @Id
    private String id;

    @ManyToOne
    private Compra compra;

    @CreationTimestamp
    private final  LocalDateTime criadoEm=LocalDateTime.now();

    @OneToMany(mappedBy = "pagamento", fetch = FetchType.EAGER)
    private List<Transacao> transacoes = new ArrayList<>();

    public Pagamento(Compra compra) {
        this.id = UUID.randomUUID().toString();
        this.compra = compra;
    }

    @Deprecated
    public Pagamento() {
    }

    public String getId() {
        return id;
    }

    public Compra getCompra() {
        return compra;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void associarTransacao(Transacao transacao){
        this.transacoes.add(transacao);

    }

    public boolean pagamentoNaoConcluido() {
        return this.transacoes.stream().filter(t -> t.getStatus().equals(Status.SUCESSO)).collect(Collectors.toList()).size() == 0;
    }

}
