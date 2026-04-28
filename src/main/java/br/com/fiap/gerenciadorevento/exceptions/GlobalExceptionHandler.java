package br.com.fiap.gerenciadorevento.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> erros.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", HttpStatus.BAD_REQUEST.value());
        resposta.put("erros", erros);

        return ResponseEntity.badRequest().body(resposta);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", ex.getStatusCode().value());
        resposta.put("erro", ex.getReason());

        return ResponseEntity.status(ex.getStatusCode()).body(resposta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        resposta.put("erro", "Erro interno: " + ex.getMessage());

        return ResponseEntity.internalServerError().body(resposta);
    }
}
