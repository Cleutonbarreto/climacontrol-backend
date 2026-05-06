package com.climacontrol.app.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrdemServicoResponseDTO {

    private Long id;

    private Long clienteId;
    private String clienteNome;

    private Long equipamentoId;
    private String equipamentoNome;

    private String descricao;

    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;

    private LocalDate dataProximaManutencao;

    private String status;
}
