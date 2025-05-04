package com.gustavosass.orders.exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionResponse> handleAllExceptions(Throwable throwable, WebRequest webRequest) {
		return new ResponseEntity<>(newExceptionResponse(throwable, webRequest), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException, WebRequest webRequest) {
		return new ResponseEntity<>(newExceptionResponse(usernameNotFoundException, webRequest), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidCredentialsException(BadCredentialsException badCredentialsException, WebRequest webRequest) {
		return new ResponseEntity<>(new ExceptionResponse("Authentication failed. Please check your credentials.", webRequest.getDescription(false)), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<ExceptionResponse> handleInternalAuthenticationServiceException(
			InternalAuthenticationServiceException internalAuthenticationServiceException, WebRequest webRequest) {
		return new ResponseEntity<>(new ExceptionResponse("Authentication failed. Please check your credentials.", webRequest.getDescription(false)), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ExceptionResponse> handleNoSuchElementException(NoSuchElementException noSuchElementException, WebRequest webRequest) {
		return new ResponseEntity<>(newExceptionResponse(noSuchElementException, webRequest), HttpStatus.NOT_FOUND);
	}

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException expiredJwtException, WebRequest webRequest) {
        return new ResponseEntity<>(new ExceptionResponse("Token expired. Please log in again.", webRequest.getDescription(false)), HttpStatus.UNAUTHORIZED);
    }

    private ExceptionResponse newExceptionResponse(Throwable throwable, WebRequest webRequest) {
		return new ExceptionResponse(throwable.getMessage(), webRequest.getDescription(false));
	}
}
