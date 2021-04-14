package br.com.zup.mercadolivre.pagamento;

import br.com.zup.mercadolivre.Client.ClienteNotaFiscal;
import br.com.zup.mercadolivre.Client.ClienteRankingVendedores;
import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.compra.Status;
import br.com.zup.mercadolivre.emailserver.EmailServer;
import br.com.zup.mercadolivre.handler.MensageReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private ClienteNotaFiscal clienteNotaFiscal;
    @Autowired
    private ClienteRankingVendedores rankingVendedores;

    private Random random = new Random();


    @PostMapping("return-paypal/{idCompra}")
    @Transactional
    public ResponseEntity<?> retornoPayPal(@PathVariable Long idCompra, HttpServletRequest request) {
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
            clienteNotaFiscal.solicitaNF(compra.getComprador().getId(),compra.getId(),request.getHeader("Authorization"));
            rankingVendedores.atualizaRanking(compra.getProduto().getUsuario().getId(), compra.getId(),request.getHeader("Authorization"));
            Integer status=novaTransacao.getStatus().ordinal();
            return ResponseEntity.ok(UriComponentsBuilder.fromUriString("/pagamentos/compras/{idCompra}/{idTransacao}&status={status}").buildAndExpand(Map.of("idCompra",compra.getId(),"idTransacao", novaTransacao.getId(),"status",status.toString())).toUriString());
        }

            Transacao transacao = new Transacao(Status.ERRO, pagamento, compra);
            manager.persist(transacao);
            String uriTenteNovamente = UriComponentsBuilder.fromUriString("/return-novopagamento/{idCompra}/{idPagamento}").buildAndExpand(Map.of("idCompra", idCompra, "idPagamento", pagamento.getId())).toUriString();
            String msg = "Pagamento não autorizado, para tentar novamente utilize este endereço:\n "+uriTenteNovamente;
            emailServer.send(compra.getComprador(), msg, compra.getProduto().getNome(), "Pagamento Não autorizado");
            Integer status=transacao.getStatus().ordinal();
            return ResponseEntity.ok(UriComponentsBuilder.fromUriString("/pagamentos/compras/{idCompra}/{idTransacao}&status={status}").buildAndExpand(Map.of("idCompra",compra.getId(),"idTransacao", transacao.getId(),"status",status.toString())).toUriString());

    }
    @PostMapping("return-pagseguro/{idCompra}")
    @Transactional
    public ResponseEntity<?> retornoPagSeguro(@PathVariable Long idCompra, HttpServletRequest request) {
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
            clienteNotaFiscal.solicitaNF(compra.getComprador().getId(),compra.getId(),request.getHeader("Authorization"));
            rankingVendedores.atualizaRanking(compra.getProduto().getUsuario().getId(), compra.getId(),request.getHeader("Authorization"));
            String status=novaTransacao.getStatus().name();
            return ResponseEntity.ok(UriComponentsBuilder.fromUriString("/pagamentos/compras/{idCompra}/{idTransacao}&status={status}").buildAndExpand(Map.of("idCompra",compra.getId(),"idTransacao", novaTransacao.getId(),"status",status)).toUriString());
        }

        Transacao transacao = new Transacao(Status.ERRO, pagamento, compra);
        manager.persist(transacao);
        String uriTenteNovamente = UriComponentsBuilder.fromUriString("/return-novopagamento/{idCompra}/{idPagamento}").buildAndExpand(Map.of("idCompra", idCompra, "idPagamento", pagamento.getId())).toUriString();
        String msg = "Pagamento não autorizado, para tentar novamente utilize este endereço:\n "+uriTenteNovamente;
        emailServer.send(compra.getComprador(), msg, compra.getProduto().getNome(), "Pagamento Não autorizado");
        String status=transacao.getStatus().name();
        return ResponseEntity.ok(UriComponentsBuilder.fromUriString("/pagamentos/compras/{idCompra}/{idTransacao}&status={status}").buildAndExpand(Map.of("idCompra",compra.getId(),"idTransacao", transacao.getId(),"status",status)).toUriString());

    }



}
