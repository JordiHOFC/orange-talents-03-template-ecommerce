package br.com.zup.mercadolivre.config.services.token;

import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationToken extends OncePerRequestFilter {

    private TokenService tokenService;

    public AuthenticationToken(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=recuperaToken(request);
        boolean valido= tokenService.isValid(token);
        if (valido){
            Usuario usuario= tokenService.getUser(token);
            UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }

    private String recuperaToken(HttpServletRequest request) {
        String token=request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }
}
