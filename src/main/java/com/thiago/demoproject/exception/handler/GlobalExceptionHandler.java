package com.thiago.demoproject.exception.handler;

import com.thiago.demoproject.exception.DataIntegrityViolationException;
import com.thiago.demoproject.exception.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleAllExceptions(Exception ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now().atZone(ZoneId.of(AMERICA_SAO_PAULO)).toLocalDateTime(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now().atZone(ZoneId.of(AMERICA_SAO_PAULO)).toLocalDateTime(), HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
