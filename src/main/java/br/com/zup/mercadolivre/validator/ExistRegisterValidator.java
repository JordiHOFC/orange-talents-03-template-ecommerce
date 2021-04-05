package br.com.zup.mercadolivre.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistRegisterValidator implements ConstraintValidator<ExistRegister,Long> {
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistRegister constraintAnnotation) {
        this.domainClass=constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        String jpql="select r from "+domainClass.getSimpleName()+" r where r.id=:id";
        Query query= manager.createQuery(jpql);
        query.setParameter("id",aLong);
        return !query.getResultList().isEmpty();
    }
}
