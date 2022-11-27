package org.kehrbusch;

import org.kehrbusch.entities.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleCustomRequest(Exception e, WebRequest request){
        if (e instanceof BadRequestException){
            logger.warn("Throw exception of class " + e.getClass());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }
}
