package br.com.zup.mercadolivre.produto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody @Valid ProdutoRequest produtoRequest){
        Produto produto= produtoRequest.toModelo();
        repository.save(produto);
        return ResponseEntity.ok().build();
    }
}
