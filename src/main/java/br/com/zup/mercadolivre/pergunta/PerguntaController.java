package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.emailserver.EmailServer;
import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.validator.ExistRegister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping
public class PerguntaController {
    private final EmailServer emailServer;
    private final ProdutoRepository produtoRepository;

    public PerguntaController(EmailServer emailServer, ProdutoRepository produtoRepository) {
        this.emailServer = emailServer;
        this.produtoRepository = produtoRepository;
    }
    @PostMapping("/produtos/{idProduto}/perguntas")
    @Transactional
    public ResponseEntity<?> cadastrarPergunta(@RequestBody @Valid PerguntaRequest perguntaRequest, @PathVariable  @ExistRegister(domainClass = Produto.class) Long idProduto, @AuthenticationPrincipal Usuario logado){

        Optional<Produto> produto=produtoRepository.findById(idProduto);
        if (produto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este produto não existe");
        }
        Pergunta pergunta= perguntaRequest.toModelo(produto.get(),logado);
        produto.get().adicionarPergunta(pergunta);
        emailServer.send(produto.get().getUsuario(),perguntaRequest.getTitulo(),logado,produto.get().getNome());
        return ResponseEntity.ok().build();
    }
}
