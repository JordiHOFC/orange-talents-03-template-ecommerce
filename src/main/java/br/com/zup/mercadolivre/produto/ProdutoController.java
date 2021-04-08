package br.com.zup.mercadolivre.produto;


import br.com.zup.mercadolivre.emailserver.EmailServer;
import br.com.zup.mercadolivre.imagem.ImageResponse;

import br.com.zup.mercadolivre.imagem.Imagem;
import br.com.zup.mercadolivre.imagem.ImagensRequest;
import br.com.zup.mercadolivre.opiniao.Opiniao;
import br.com.zup.mercadolivre.opiniao.OpiniaoRequest;
import br.com.zup.mercadolivre.pergunta.Pergunta;
import br.com.zup.mercadolivre.pergunta.PerguntaRequest;
import br.com.zup.mercadolivre.uploaderimageserver.UploaderImageServer;
import br.com.zup.mercadolivre.usuario.Usuario;

import br.com.zup.mercadolivre.validator.ExistRegister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;
    private final EmailServer emailServer;
    private final UploaderImageServer uploaderImageServer;

    public ProdutoController(ProdutoRepository repository, EmailServer emailServer, UploaderImageServer uploaderImageServer) {
        this.repository = repository;
        this.emailServer = emailServer;
        this.uploaderImageServer = uploaderImageServer;
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
        List<Imagem> novasImagens=imagensRequest.getImagens(uploaderImageServer);
        possivelProduto.get().adicionarImagem(novasImagens);
        return ResponseEntity.ok(novasImagens.stream().map(ImageResponse::new).collect(Collectors.toList()));
    }

    @PatchMapping("/{idProduto}/opinioes")
    @Transactional
    public ResponseEntity<?> cadastrarOpiniao(@RequestBody @Valid OpiniaoRequest opiniaoRequest, @PathVariable Long idProduto, @AuthenticationPrincipal Usuario logado){

        Optional<Produto> produto=repository.findById(idProduto);

        if (produto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este Produto não existe");
        }
        Opiniao opiniao=opiniaoRequest.toModelo(produto.get(),logado);
        produto.get().adicionarOpiniao(opiniao);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{idProduto}/perguntas")
    @Transactional
    public ResponseEntity<?> cadastrarPergunta(@RequestBody @Valid PerguntaRequest perguntaRequest, @PathVariable  @ExistRegister(domainClass = Produto.class) Long idProduto, @AuthenticationPrincipal Usuario logado){

        Optional<Produto> produto=repository.findById(idProduto);
        if (produto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este produto não existe");
        }
        Pergunta pergunta= perguntaRequest.toModelo(produto.get(),logado);
        produto.get().adicionarPergunta(pergunta);
        emailServer.send(produto.get().getUsuario(),perguntaRequest.getTitulo(),logado,produto.get().getNome());
        return ResponseEntity.ok().build();
    }
}
