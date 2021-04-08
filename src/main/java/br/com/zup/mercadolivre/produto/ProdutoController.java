package br.com.zup.mercadolivre.produto;


import br.com.zup.mercadolivre.imagem.ImageResponse;

import br.com.zup.mercadolivre.imagem.ImagensRequest;
import br.com.zup.mercadolivre.opiniao.Opiniao;
import br.com.zup.mercadolivre.opiniao.OpiniaoRequest;
import br.com.zup.mercadolivre.pergunta.Pergunta;
import br.com.zup.mercadolivre.pergunta.PerguntaRequest;
import br.com.zup.mercadolivre.usuario.Usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
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
        possivelProduto.get().adicionarImagem(imagensRequest.getImagens());
        return ResponseEntity.ok(imagensRequest.getImagens().stream().map(ImageResponse::new).collect(Collectors.toList()));
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
    public ResponseEntity<?> cadastrarPergunta(@RequestBody @Valid PerguntaRequest perguntaRequest, @PathVariable Long idProduto, @AuthenticationPrincipal Usuario logado){

        Optional<Produto> produto=repository.findById(idProduto);
        if (produto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este produto não existe");
        }
        Pergunta pergunta= perguntaRequest.toModelo(produto.get(),logado);
        produto.get().adicionarPergunta(pergunta);
        escreverEmail(perguntaRequest, logado, produto);
        return ResponseEntity.ok().build();


    }

    private void escreverEmail(PerguntaRequest perguntaRequest, Usuario logado, Optional<Produto> produto) {
        String Email="Remetente: "+ logado.getUsername()+" \n Destinatario: "+ produto.get().
        getUsuario().getUsername()+
        "\n Assunto: Pergunta sobre produto: "+ produto.get().getNome()+"\n "+
        "Pergunta: "+ perguntaRequest.getTitulo()+"\n";
        System.out.println(Email);
    }

}
