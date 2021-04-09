package br.com.zup.mercadolivre.config.token;

import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.usuario.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${mercadolivre.jwt.secret}")
    private String secret;
    @Value("${mercadolivre.jwt.expiration}")
    private String expiration;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String gerarToken(Authentication auth){
        Date hoje=new Date();
        Date dateExpiration=new Date(hoje.getTime()+Long.parseLong(expiration));
        Usuario usuarioLogado= (Usuario) auth.getPrincipal();
        return Jwts.builder().
                setIssuer("API DO MERCADO LIVRE")
                .setSubject(usuarioLogado.getUsername())
                .setIssuedAt(hoje)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }
    public boolean isValid(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public Usuario getUser(String token){
        String login=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return usuarioRepository.findByLogin(login).get();
    }
}
