package br.com.zup.mercadolivre.login;

import br.com.zup.mercadolivre.config.services.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken login= loginRequest.toAuthentication();
        Authentication auth=authenticationManager.authenticate(login);
        String token=tokenService.gerarToken(auth);
        return ResponseEntity.ok().headers(header->header.setBearerAuth(token)).build();
    }
}
