package com.hr.api.common.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.hr.api.common.response.ApiError;
import com.hr.api.multitenancy.excption.DatabaseUsernameAlreadyExists;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        
        return buildResponse("Validation Error", errors, HttpStatus.BAD_REQUEST, ex.getClass().getSimpleName(), request);
    }
    
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiError> handleValidationException(ValidationException ex, WebRequest request) {
	    
		return buildResponse(
	        ex.getMessage() != null ? ex.getMessage() : "Validation failed",
	        HttpStatus.BAD_REQUEST,
	        ex.getClass().getSimpleName(),
	        request
	    );
	}
	
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(
            EntityNotFoundException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, request);
    }

 
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBadJson(HttpMessageNotReadableException ex, WebRequest request) {
        return buildResponse("Malformed JSON or missing fields", HttpStatus.BAD_REQUEST, ex.getClass().getSimpleName(), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        return buildResponse("Access denied", HttpStatus.FORBIDDEN, ex.getClass().getSimpleName(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request) {
        return buildResponse("Data integrity violation", HttpStatus.CONFLICT, ex.getClass().getSimpleName(), request);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ApiError> handelPropertyReferenceException(PropertyReferenceException ex, WebRequest request) {
    	
    	return buildResponse(ex, HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        return buildResponse("Login Faild", HttpStatus.FORBIDDEN, ex.getClass().getSimpleName(), request);
    }
    
    
    @ExceptionHandler(DatabaseUsernameAlreadyExists.class)
    public ResponseEntity<ApiError> handleDatabaseUsernameAlreadyExists(DatabaseUsernameAlreadyExists ex, WebRequest request) {
    	
    	 Map<String, String> errors = new HashMap<>();
    	 
    	 errors.put(ex.getFieldName(), ex.getMessage());
    	
    	return buildResponse("Validation Error", errors, HttpStatus.BAD_REQUEST, ex.getClass().getSimpleName(), request);
    }
   
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleAllUncaught(IllegalArgumentException ex, WebRequest request) {
		String message = ex.getMessage();
		
    	if(message.startsWith("{") && message.endsWith("}")) {
			 
    		message = message.substring(1, message.length() -1);
			message = messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    	}
		 
        return buildResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getSimpleName(), request);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllUncaught(Exception ex, WebRequest request) {
    	
    	log.error(ex.getMessage(), ex);
    	    
        return buildResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getSimpleName(), request);
    }
    
 
    // 🔧 Helper method to build consistent response
    private ResponseEntity<ApiError> buildResponse(Exception ex, HttpStatus status, WebRequest request) {
        return buildResponse(ex.getMessage(), status, ex.getClass().getSimpleName(), request);
    }

    private ResponseEntity<ApiError> buildResponse(String message, HttpStatus status, String errorType, WebRequest request) {
   
        return buildResponse(message, new HashMap<>(), status, errorType,request);
    }
    
    
    
    private ResponseEntity<ApiError> buildResponse(String message, Map<String, String> messages, HttpStatus status, String errorType, WebRequest request) {
        ApiError apiError = new ApiError(
                status.value(),
                message,
                messages,
                errorType,
                LocalDateTime.now(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        return new ResponseEntity<>(apiError, status);
    }
    
    

}
