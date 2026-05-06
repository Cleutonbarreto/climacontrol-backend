package com.climacontrol.app.exceptions;

public class UsuarioInativoException extends RuntimeException {
    public UsuarioInativoException() {
        super("Usuário inativo");
    }
}
