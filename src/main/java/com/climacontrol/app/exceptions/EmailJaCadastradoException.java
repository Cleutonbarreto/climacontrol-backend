package com.climacontrol.app.exceptions;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException () {
        super("Email já cadastrado");
    }
}
