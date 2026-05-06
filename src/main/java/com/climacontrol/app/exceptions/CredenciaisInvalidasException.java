package com.climacontrol.app.exceptions;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException() {
        super("Email ou senha inválidos");
    }
}
