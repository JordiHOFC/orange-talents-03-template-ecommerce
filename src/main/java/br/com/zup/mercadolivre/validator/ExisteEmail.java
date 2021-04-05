package br.com.zup.mercadolivre.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisteEmailValidator.class)
public @interface ExisteEmail {
    String message() default "Já existe um usuario cadastrado com este endereço de email.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
