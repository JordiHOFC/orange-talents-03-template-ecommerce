package br.com.zup.mercadolivre.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValueValidator.class)
public @interface UniqueValue {
    String message() default "Informação já cadastrada no sistema";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> domainClass();
    String field();
}
