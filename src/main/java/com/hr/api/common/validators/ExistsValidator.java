package com.hr.api.common.validators;

import java.util.List;

import com.hr.api.common.annotation.Exists;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsValidator implements ConstraintValidator<Exists, Object> {

    private Class<?> entityClass;
    private String fieldName;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(Exists annotation) {
        this.entityClass = annotation.entity();
        this.fieldName = annotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        
    	if (value == null) return true; 
    	   	 
    	 
    	 String jpql = String.format(
                 "SELECT COUNT(e) FROM %s e WHERE e.%s = :value",
                 entityClass.getName(), fieldName
             );

         Long count = entityManager.createQuery(jpql, Long.class)
                 .setParameter("value", value)
                 .getSingleResult();

         return count != null &&  count > 0;
    }
}
