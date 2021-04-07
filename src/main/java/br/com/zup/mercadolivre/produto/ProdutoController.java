package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.config.services.token.TokenService;
import br.com.zup.mercadolivre.imagem.ImageResponse;
import br.com.zup.mercadolivre.imagem.Imagem;
import br.com.zup.mercadolivre.imagem.ImagensRequest;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;


    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody @Valid ProdutoRequest produtoRequest, @AuthenticationPrincipal
            Usuario logado){
        Produto produto= produtoRequest.toModelo();
        produto.setUsuario(logado);
        repository.save(produto);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{idProduto}/imagens")
    @Transactional
    public ResponseEntity<?> cadastrarImagensNoProduto(@Valid ImagensRequest imagensRequest, @PathVariable Long idProduto, @AuthenticationPrincipal
            Usuario logado){

        Optional<Produto> possivelProduto=repository.findById(idProduto);
        if (possivelProduto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não existente");
        }

        Usuario donoProduto=possivelProduto.get().getUsuario();
        if(!logado.getUsername().equals(donoProduto.getUsername())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Este produto não pertence ao usuario que esta em sessão.");
        }
        possivelProduto.get().getImagems().addAll(imagensRequest.getImagens());
       // possivelProduto.get().getImagems().addAll(imagensRequest.stream().map(Imagem::new).collect(Collectors.toList()));
        return ResponseEntity.ok(possivelProduto.get().getImagems().stream().map( i-> new ImageResponse(possivelProduto.get().getNome(),i.getNome(),i.getUrl())).collect(Collectors.toList()));
    }

}
