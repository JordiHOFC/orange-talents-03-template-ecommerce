package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.config.token.TokenService;
import br.com.zup.mercadolivre.emailserver.EmailServerImpl;
import br.com.zup.mercadolivre.handler.Errors;
import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.Optional;

@RestController
@RequestMapping("/compras")
public class CompraController {
    private final ProdutoRepository repository;
    @PersistenceContext private EntityManager entityManager;

    public CompraController(ProdutoRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarVendaDeProduto(@RequestBody @Valid CompraRequest compraRequest, @AuthenticationPrincipal Usuario comprador, @Autowired EmailServerImpl server, UriComponentsBuilder uriComponentsBuilder){
        Optional<Produto> possivelProduto=repository.findById(compraRequest.getIdProduto());
        if (possivelProduto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Errors("Produto","Produto não encontrado."));
        }
        Produto produto= possivelProduto.get();
        Integer quantidadeDisponivel=possivelProduto.get().getQuantidade();
        Usuario vendedor=possivelProduto.get().getUsuario();
        Compra compra=compraRequest.toModelo(comprador);


        if (!produto.adicionarVenda(compra, compraRequest.getQuantidade())){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Errors("Quantidade","Quantidade indisponivel"));
        }
        entityManager.persist(compra);
        server.send(vendedor,"Processo de compra iniciado",comprador,possivelProduto.get().getNome(),"Compra");
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(compra.getMetodoPagamento().criaUrlRetorno(compra)).build();
    }
}
