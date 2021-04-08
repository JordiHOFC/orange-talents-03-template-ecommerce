package br.com.zup.mercadolivre.imagem;


import br.com.zup.mercadolivre.produto.Produto;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
public class Imagem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String url;


    public Imagem(String nome, String url) {
        this.nome = nome;
        this.url = url;
    }

    public Imagem() {
    }

    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }
}
