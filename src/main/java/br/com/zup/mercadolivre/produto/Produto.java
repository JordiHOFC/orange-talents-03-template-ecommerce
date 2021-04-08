package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.imagem.Imagem;
import br.com.zup.mercadolivre.opiniao.Opiniao;
import br.com.zup.mercadolivre.pergunta.Pergunta;
import br.com.zup.mercadolivre.produto.caracteristicas.Caracteristica;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valor;

    @Type(type = "materialized_nclob")
    @Column(nullable = false)
    private String descricao;

    @PastOrPresent
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Caracteristica>caracteristicas=new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Imagem> imagems=new ArrayList<>();

    @OneToMany(mappedBy = "produto",cascade = CascadeType.ALL)
    private List<Opiniao> opinoes= new ArrayList<>();

    @OneToMany(mappedBy = "produto",cascade = CascadeType.ALL)
    private List<Pergunta> perguntas= new ArrayList<>();

    public Produto(String nome, BigDecimal valor, String descricao, Integer quantidade, Categoria categoria, List<Caracteristica> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade=quantidade;
        this.descricao = descricao;
        this.criadoEm = LocalDateTime.now();
        this.categoria = categoria;
        this.caracteristicas = caracteristicas;
    }

    public Produto() {
    }

    public Produto(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarImagem(List<Imagem> imagems){
        this.imagems.addAll(imagems);
    }
    public void adicionarOpiniao(Opiniao opiniao){
        this.opinoes.add(opiniao);
    }
    public void adicionarPergunta(Pergunta pergunta){this.perguntas.add(pergunta);}

    public List<Imagem> getImagems() {
        return imagems;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
