package com.climacontrol.app.dto;

public class EquipamentoResponseDTO {
    private Long id;
    private String tipo;
    private Integer capacidade;
    private String marca;
    private String modelo;

    // 🔥 info do cliente (resumido)
    private Long clienteId;
    private String clienteNome;

    public EquipamentoResponseDTO(Long id, String tipo, Integer capacidade,
                                  String marca, String modelo,
                                  Long clienteId, String clienteNome) {
        this.id = id;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.marca = marca;
        this.modelo = modelo;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
    }

    // getters

    public Long getId() { return id; }

    public String getTipo() { return tipo; }

    public Integer getCapacidade() { return capacidade; }

    public String getMarca() { return marca; }

    public String getModelo() { return modelo; }

    public Long getClienteId() { return clienteId; }

    public String getClienteNome() { return clienteNome; }
}
