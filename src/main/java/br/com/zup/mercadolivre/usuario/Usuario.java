package br.com.zup.mercadolivre.usuario;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @Column(nullable = false,unique = true)
    private String login;

    @Column
    private String senha;
    @PastOrPresent
    private LocalDateTime criadoEm;

    public Usuario(@Email String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.criadoEm=LocalDateTime.now();
    }

    public Usuario() {
    }
}
