package br.com.zup.mercadolivre.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue,Object> {
    private Class<?> domainClass;
    private String domainAtribute;
    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.domainAtribute= constraintAnnotation.field();
        this.domainClass=constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String jpql="select 1 from "+domainClass.getSimpleName()+" r where "+domainAtribute+" =:register";
        Query query= manager.createQuery(jpql);
        query.setParameter("register",o);
        return query.getResultList().isEmpty();
    }
}
