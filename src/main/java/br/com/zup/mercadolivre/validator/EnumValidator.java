package br.com.zup.mercadolivre.validator;

import br.com.zup.mercadolivre.compra.MetodoPagamento;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumSet;
import java.util.List;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidateEnum,String> {
    private List<String>  allowedValues;
    private  Class<? extends Enum> enumSelected;

    @Override
    public void initialize(ValidateEnum targetEnum) {
        this.enumSelected = targetEnum.targetClassType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        allowedValues =  (List<String>) EnumSet.allOf(enumSelected).stream().map(e -> ((Enum<? extends Enum<?>>) e).name()).collect(Collectors.toList());
        return allowedValues.contains(value);
    }


}
