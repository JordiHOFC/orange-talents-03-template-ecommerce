package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Opiniao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private Integer nota;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Usuario usuario;

    public Opiniao(String titulo, String descricao, Integer nota) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.nota = nota;
    }
    public Opiniao(String titulo, String descricao, Integer nota, Produto produto,Usuario usuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.nota = nota;
        this.produto=produto;
        this.usuario=usuario;

    }

    public Opiniao() {
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

}
