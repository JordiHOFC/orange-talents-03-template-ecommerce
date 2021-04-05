package br.com.zup.mercadolivre.categoria;

import javax.persistence.*;

@Entity
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne
    @JoinColumn(unique = true, nullable = true)
    private Categoria categoriaMae;

    public Categoria(String nome, Categoria categoria) {
        this.nome = nome;
        this.categoriaMae = categoria;
    }

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Categoria(Long id) {
        this.id = id;
    }

    public Categoria() {
    }
}
