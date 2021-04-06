package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.produto.caracteristicas.Caracteristica;
import com.sun.istack.Nullable;
import org.hibernate.annotations.Type;
import org.springframework.data.util.Pair;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Caracteristica>caracteristicas=new ArrayList<>();

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
}
