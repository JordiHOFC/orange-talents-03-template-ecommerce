package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Produto produto;

    @CreationTimestamp
    @PastOrPresent
    private LocalDateTime criadoEm=LocalDateTime.now();

    public Pergunta(String titulo, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
    }

    public Pergunta() {
    }

    public String getTitulo() {
        return titulo;
    }
}
