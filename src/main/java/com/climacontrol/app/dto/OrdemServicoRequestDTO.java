package com.climacontrol.app.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrdemServicoRequestDTO {

    private Long clienteId;
    private Long equipamentoId;

    private String descricao;

    private LocalDate dataProximaManutencao;
}
