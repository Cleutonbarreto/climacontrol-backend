package com.climacontrol.app.exceptions;

import com.climacontrol.app.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailJaCadastrado(
            EmailJaCadastradoException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        409,
                        "CONFLICT",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErrorResponseDTO> handleCredenciaisInvalidas(
            CredenciaisInvalidasException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO(
                        401,
                        "UNAUTHORIZED",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UsuarioInativoException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsuarioInativo(
            UsuarioInativoException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDTO(
                        403,
                        "FORBIDDEN",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Throwable ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(
                        500,
                        "INTERNAL_SERVER_ERROR",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(DocumentoJaCadastradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleCpfCnpjJaCadastrado(
            DocumentoJaCadastradoException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        409,
                        "CONFLICT",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(DocumentoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleDocumentoInvalido(
            DocumentoInvalidoException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        400,
                        "BAD_REQUEST",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMediaType(
            HttpMediaTypeNotSupportedException ex) {

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ErrorResponseDTO(
                        415,
                        "UNSUPPORTED_MEDIA_TYPE",
                        "Content-Type deve ser application/json"
                ));
    }
}
