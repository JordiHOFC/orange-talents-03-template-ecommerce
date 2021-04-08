package br.com.zup.mercadolivre.produto.caracteristicas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Caracteristica {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;

    public Caracteristica(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Caracteristica() {
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
