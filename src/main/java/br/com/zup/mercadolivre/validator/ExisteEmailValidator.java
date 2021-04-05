package br.com.zup.mercadolivre.validator;

import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ExisteEmailValidator implements ConstraintValidator<ExisteEmail,String> {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Usuario> usuario=repository.findByLogin(email);
        return usuario.isEmpty();
    }
}
