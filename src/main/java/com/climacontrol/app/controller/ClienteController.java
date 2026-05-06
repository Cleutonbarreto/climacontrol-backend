package com.climacontrol.app.controller;

import com.climacontrol.app.dto.ClienteRequestDTO;
import com.climacontrol.app.dto.ClienteResponseDTO;
import com.climacontrol.app.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // Criar cliente
    @PostMapping
    public ClienteResponseDTO criar(@RequestBody @Valid ClienteRequestDTO dto) {
        return service.criar(dto);
    }

    //  Listar todos
    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return service.listar();
    }

    //  Buscar por ID
    @GetMapping("/{id}")
    public ClienteResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    //  Atualizar
    @PutMapping("/{id}")
    public ClienteResponseDTO atualizar(@PathVariable Long id,
                                        @RequestBody @Valid ClienteRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    //  Deletar
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}