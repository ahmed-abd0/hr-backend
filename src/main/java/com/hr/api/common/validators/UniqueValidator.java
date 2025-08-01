package com.hr.api.common.validators;

import org.springframework.stereotype.Component;

import com.hr.api.common.annotation.Unique;
import com.hr.api.common.util.RequestUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private String fieldName;
    private Class<?> entityClass;
    private boolean updating;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.entityClass = constraintAnnotation.entity();
        this.updating = constraintAnnotation.updating();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        
        String jpql = "SELECT COUNT(e) FROM " + entityClass.getName() + " e WHERE e." + fieldName + " = :value";
        
        if(this.updating) {
        	jpql = jpql + " AND e.id != :id";
        }
        
        Query query = entityManager.createQuery(jpql);
        
        if(this.updating) {
        	  query.setParameter("id", RequestUtil.getIdFromPath());
        }
       
        query.setParameter("value", value);

        Long count = (Long) query.getSingleResult();
        return count == 0;
    }
}

