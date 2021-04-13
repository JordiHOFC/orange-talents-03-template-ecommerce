package br.com.zup.mercadolivre.usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario implements UserDetails {
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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> permissoes=new ArrayList<>();

    public Usuario(@Email String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.criadoEm=LocalDateTime.now();
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissoes;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(login, usuario.login) && Objects.equals(senha, usuario.senha) && Objects.equals(criadoEm, usuario.criadoEm) && Objects.equals(permissoes, usuario.permissoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, senha, criadoEm, permissoes);
    }
}
