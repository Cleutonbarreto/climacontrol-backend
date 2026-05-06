package com.climacontrol.app.controller;

import com.climacontrol.app.dto.EquipamentoRequestDTO;
import com.climacontrol.app.dto.EquipamentoResponseDTO;
import com.climacontrol.app.services.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService service;

    public EquipamentoController(EquipamentoService service) {
        this.service = service;
    }
    // Criar equipamento
    @PostMapping
    public EquipamentoResponseDTO criar(@RequestBody @Valid EquipamentoRequestDTO dto) {
        return service.criar(dto);
    }

    // Listar todos
    @GetMapping
    public List<EquipamentoResponseDTO> listar() {
        return service.listar();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public EquipamentoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Atualizar
    @PutMapping("/{id}")
    public EquipamentoResponseDTO atualizar(@PathVariable Long id,
                                            @RequestBody @Valid EquipamentoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    // Deletar
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    // EXTRA (muito útil)
    // Buscar equipamentos por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<EquipamentoResponseDTO> listarPorCliente(@PathVariable Long clienteId) {
        return service.listarPorCliente(clienteId);
    }
}
