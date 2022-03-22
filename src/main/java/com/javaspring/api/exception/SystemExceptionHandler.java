package com.javaspring.api.exception;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javaspring.api.constants.CustomMessages;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

@RestControllerAdvice
public class SystemExceptionHandler {

	@ExceptionHandler(WebExchangeBindException.class)
    public final ResponseEntity<ErrorResponse> handleValidationExceptions(
            WebExchangeBindException exception) {

        List<Error> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(new Error(ErrorCode.PRODUCT_VALIDATION_ERROR,
                        error.getField(), error.getDefaultMessage())));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
	

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponse> handleSystemException(SystemException ex) {
        List<Error> errors = new ArrayList<>();
        errors.add(new Error(ex.getErrorCode(), "", ex.getErrorCode().getMessage()));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    
}
