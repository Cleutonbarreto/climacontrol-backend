package com.climacontrol.app.dto;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String token;
    private String role;


    public UsuarioResponseDTO(Long id, String nome, String email, String token, String role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.token = token;
        this.role = role;


    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    private String getRole() {
        return role;
    }
}
