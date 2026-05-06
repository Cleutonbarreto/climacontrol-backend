package com.climacontrol.app.controller;

import com.climacontrol.app.dto.OrdemServicoRequestDTO;
import com.climacontrol.app.dto.OrdemServicoResponseDTO;
import com.climacontrol.app.enums.StatusOrdemServico;
import com.climacontrol.app.services.OrdemServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordens-servico")
@RequiredArgsConstructor
public class OrdemServicoController {

    private final OrdemServicoService service;

    // Criar OS
    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> criar(@RequestBody @Valid OrdemServicoRequestDTO dto) {
        OrdemServicoResponseDTO response = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Listar todas OS
    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> listar(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long clienteId
    ) {


        if (status != null && clienteId != null) {
            return ResponseEntity.ok(
                    service.buscarPorStatusECliente(
                            StatusOrdemServico.valueOf(status),
                            clienteId
                    )
            );
        }

        // só status
        if (status != null) {
            return ResponseEntity.ok(
                    service.buscarPorStatus(StatusOrdemServico.valueOf(status))
            );
        }

        // só cliente
        if (clienteId != null) {
            return ResponseEntity.ok(
                    service.buscarPorCliente(clienteId)
            );
        }

        //  tudo
        return ResponseEntity.ok(service.listar());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Atualizar OS
    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> atualizar(@PathVariable Long id,
                                                             @RequestBody @Valid OrdemServicoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // Finalizar OS
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<OrdemServicoResponseDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizar(id));
    }

    // Cancelar OS
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<OrdemServicoResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }

}
