package com.hr.api.common.validators;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.ResourceBundleMessageResolver;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.context.i18n.LocaleContextHolder;

import com.hr.api.common.annotation.StrongPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PassayPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        
    	if (password == null) return false;

        PasswordValidator validator = new PasswordValidator(
           getMessageResolver(),
            List.of(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
            )
        );

        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
         
        	context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                String.join(", ", validator.getMessages(result))
            ).addConstraintViolation();
        }

        return result.isValid();
    }
    
    
    private MessageResolver getMessageResolver() {
    	
    	try {
            return new ResourceBundleMessageResolver(
                ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale())
            );
        } catch (MissingResourceException ex) {
            return new ResourceBundleMessageResolver(
                ResourceBundle.getBundle("messages", Locale.ENGLISH)
            );
        }
         
	}
}