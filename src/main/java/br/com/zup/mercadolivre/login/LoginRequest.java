package br.com.zup.mercadolivre.login;

import br.com.zup.mercadolivre.validator.ExistRegister;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {
    @Email
    @NotBlank
    @JsonProperty("login")
    private String login;
    @NotBlank
    @Size(min=6)
    @JsonProperty("senha")
    private String senha;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(this.login,this.senha);
    }
}
