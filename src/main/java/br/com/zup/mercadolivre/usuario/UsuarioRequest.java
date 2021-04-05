package br.com.zup.mercadolivre.usuario;

import br.com.zup.mercadolivre.validator.ExisteEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioRequest {
    @JsonProperty("login")
    @NotBlank
    @ExisteEmail
    @Email(message = "O login deve estar no formato de email")
    private String login;
    @JsonProperty("senha")
    @Size(min = 6)
    private String senha;

    public Usuario toModelo(){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        return new Usuario(login,encoder.encode(senha));
    }
}
