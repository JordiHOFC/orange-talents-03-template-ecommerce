package br.com.zup.mercadolivre.pagamento;

import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.compra.Status;
import br.com.zup.mercadolivre.emailserver.EmailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.Random;

@RestController
public class PagamentosController {
    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private EmailServer emailServer;

    private Random random = new Random();


    @PostMapping("return-paypal/{idCompra}")
    @Transactional
    public void retornoPayPal(@PathVariable Long idCompra) {
        Compra compra = manager.find(Compra.class, idCompra);
        Pagamento pagamento = new Pagamento(compra);
        manager.persist(pagamento);
        if (random.nextDouble() <= 0.7 && pagamento.pagamentoNaoConcluido()) {
            Transacao novaTransacao = new Transacao(Status.SUCESSO, pagamento, compra);
            manager.persist(novaTransacao);
            BigDecimal quantidade = new BigDecimal(compra.getQuantidade().toString());

            String msg = emailServer.createMessageCompra(compra.getProduto().getNome(), compra.getValor().multiply(quantidade).toString(), compra.getMetodoPagamento().name());
            emailServer.send(compra.getComprador(), msg, compra.getProduto().getNome(), "Compra finalizada com sucesso!");
            pagamento.associarTransacao(novaTransacao);
            compra.finalizarCompra(Status.SUCESSO);
            URI uriNotaFiscal = UriComponentsBuilder.fromUriString("/compras/{idCompra}/{idUsuario}").
                    port(8080).buildAndExpand(Map.of("idCompra", compra.getId(), "idUsuario", compra.getComprador().getId())).toUri();
            URI uriRankingVendedores = UriComponentsBuilder.fromUriString("/rankingvendedores/{idCompra}/{idUsuario}").
                    port(8080).buildAndExpand(Map.of("idCompra", compra.getId(), "idUsuario", compra.getComprador().getId())).toUri();
        } else if (random.nextDouble() > 0.7 && pagamento.pagamentoNaoConcluido()) {
            Transacao novaTransacao = new Transacao(Status.ERRO, pagamento, compra);
            manager.persist(novaTransacao);
            String uriTenteNovamente = UriComponentsBuilder.fromUriString("/return-novopagamento/{idCompra}/{idPagamento}").buildAndExpand(Map.of("idCompra", idCompra, "idPagamento", pagamento.getId())).toUriString();
            String msg = "Pagamento não autorizado, para tentar novamente utilize este endereço:\n "+uriTenteNovamente;
            emailServer.send(compra.getComprador(), msg, compra.getProduto().getNome(), "Pagamento Não autorizado");

        }


    }

    @PostMapping("return-pagseguro/{idCompra}")
    public void retornoPagSeguro(@PathVariable Long idCompra) {

    }

    @PostMapping("return-novopagamento/{idCompra}/{idPagamento}")
    public void novaTentativaPagamentoPaypal(@PathVariable Long idCompra, @PathVariable Long idPagamento) {

    }
}
