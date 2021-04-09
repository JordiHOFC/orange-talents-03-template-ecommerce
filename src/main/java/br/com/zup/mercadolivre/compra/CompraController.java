package br.com.zup.mercadolivre.compra;

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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class CompraController {
    private final ProdutoRepository repository;

    public CompraController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/{idProduto}/compras")
    @Transactional
    public ResponseEntity<?> cadastrarVendaDeProduto(@RequestBody @Valid CompraRequest compraRequest, @PathVariable Long idProduto, @AuthenticationPrincipal Usuario comprador, @Autowired EmailServerImpl server, UriComponentsBuilder uriComponentsBuilder){
        Optional<Produto> produto=repository.findById(idProduto);
        if (produto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Errors("Produto","Produto não encontrado."));
        }
        Integer quantidadeDisponivel=produto.get().getQuantidade();
        if (compraRequest.getQuantidade()>quantidadeDisponivel){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Errors("Quantidade","Quantidade indisponivel"));
        }
        Usuario vendedor=produto.get().getUsuario();

        Compra compra=compraRequest.toModelo(comprador, produto.get());

        server.send(vendedor,"Processo de compra iniciado",comprador,produto.get().getNome(),"Compra");

        produto.get().adicionarVenda(compra, compraRequest.getQuantidade());

        URI uri=uriComponentsBuilder.path(compraRequest.getMetodoPagamento()+".com\\?buyerId={id}&redirectUrl={localhost:8080\\/auth}").buildAndExpand(compra.getId()).toUri();

        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(uri).build();



    }
}
