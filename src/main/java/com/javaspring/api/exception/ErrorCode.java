package com.javaspring.api.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	PRODUCT_NOT_FOUND(INTERNAL_SERVER_ERROR, "System error"),
	INSTOCK_CANNOT_BE_NEGATIVE(INTERNAL_SERVER_ERROR, "System error"),
	RESERVE_CANNOT_BE_NEGATIVE(INTERNAL_SERVER_ERROR, "System error"),
	DEMAND_CANNOT_BE_NEGATIVE(INTERNAL_SERVER_ERROR, "System error"),
    CANNOT_BE_NULL(INTERNAL_SERVER_ERROR, "System error"),
    PRODUCTSKU_MUST_BE_UNIQUE(INTERNAL_SERVER_ERROR, "System error"),
    PRODUCT_VALIDATION_ERROR(BAD_REQUEST, "Validation error"),
    PRODUCTS_AUTHENTICATION_ERROR(UNAUTHORIZED, "Authentication failed"),
    ENTITY_NOT_FOUND(NOT_FOUND, "Entity not found"),
    PRODUCTS_INTERNAL_ERROR(INTERNAL_SERVER_ERROR,
            "Operation failed because of an internal network error"),
    PRODUCTS_AUTHORIZATION_ERROR(UNAUTHORIZED, "Unauthorised error"),
    PRODUCTS_CONFLICT(CONFLICT, "Contact type mismatch for a given account type"),
    PRODUCTS_DOWNSTREAM_SERVICE_TIMEOUT_ERROR(SERVICE_UNAVAILABLE,
            "Downstream profile Service timed out attempting operation"),
    PRODUCTS_MEDIA_TYPE_NOT_SUPPORTED(UNSUPPORTED_MEDIA_TYPE, "Media type not supported");
    private final HttpStatus httpStatus;
    private final String message;
}
