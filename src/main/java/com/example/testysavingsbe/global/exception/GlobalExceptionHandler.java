package com.example.testysavingsbe.global.exception;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 인자로 접근할경우 예외처리
     */
    @ExceptionHandler({IllegalArgumentException.class, BadRequestException.class, EntityNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.BAD_REQUEST);
        map.put("message", e.getMessage());

        return ResponseEntity.badRequest().body(map);
    }

    /**
     * 유효성 검사 실패 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        errors.put("code", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Not Found 예외 처리
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(NoResourceFoundException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("code", HttpStatus.NOT_FOUND);
        errors.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    /**
     * 기타 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
