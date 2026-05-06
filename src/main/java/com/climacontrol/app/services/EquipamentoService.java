package com.climacontrol.app.services;

import com.climacontrol.app.dto.EquipamentoRequestDTO;
import com.climacontrol.app.dto.EquipamentoResponseDTO;
import com.climacontrol.app.entities.Cliente;
import com.climacontrol.app.entities.Equipamento;
import com.climacontrol.app.repositories.ClienteRepository;
import com.climacontrol.app.repositories.EquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final ClienteRepository clienteRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository,
                              ClienteRepository clienteRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.clienteRepository = clienteRepository;
    }

    public EquipamentoResponseDTO criar(EquipamentoRequestDTO dto) {
        // buscar cliente
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Equipamento equipamento = new Equipamento();
        equipamento.setTipo(dto.getTipo());
        equipamento.setCapacidade(dto.getCapacidade());
        equipamento.setMarca(dto.getMarca());
        equipamento.setModelo(dto.getModelo());

        // vinculo
        equipamento.setCliente(cliente);

        equipamento = equipamentoRepository.save(equipamento);

        return mapToResponse(equipamento);
    }


    //  Listar
    public List<EquipamentoResponseDTO> listar() {
        return equipamentoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  Buscar por ID
    public EquipamentoResponseDTO buscarPorId(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        return mapToResponse(equipamento);
    }

    //  Atualizar
    public EquipamentoResponseDTO atualizar(Long id, EquipamentoRequestDTO dto) {

        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        equipamento.setTipo(dto.getTipo());
        equipamento.setCapacidade(dto.getCapacidade());
        equipamento.setMarca(dto.getMarca());
        equipamento.setModelo(dto.getModelo());
        equipamento.setCliente(cliente);

        equipamento = equipamentoRepository.save(equipamento);

        return mapToResponse(equipamento);
    }

    // Deletar
    public void deletar(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        equipamentoRepository.delete(equipamento);
    }

    public List<EquipamentoResponseDTO> listarPorCliente(Long clienteId) {
        return equipamentoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================
    //  MAPPER
    // =========================

    private EquipamentoResponseDTO mapToResponse(Equipamento equipamento) {
        return new EquipamentoResponseDTO(
                equipamento.getId(),
                equipamento.getTipo(),
                equipamento.getCapacidade(),
                equipamento.getMarca(),
                equipamento.getModelo(),
                equipamento.getCliente().getId(),
                equipamento.getCliente().getNome()
        );
    }
}
