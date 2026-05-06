package com.climacontrol.app.services;

import com.climacontrol.app.dto.OrdemServicoRequestDTO;
import com.climacontrol.app.dto.OrdemServicoResponseDTO;
import com.climacontrol.app.entities.Cliente;
import com.climacontrol.app.entities.Equipamento;
import com.climacontrol.app.entities.OrdemServico;
import com.climacontrol.app.enums.StatusOrdemServico;
import com.climacontrol.app.repositories.ClienteRepository;
import com.climacontrol.app.repositories.EquipamentoRepository;
import com.climacontrol.app.repositories.OrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final ClienteRepository clienteRepository;
    private final EquipamentoRepository equipamentoRepository;

    public OrdemServicoResponseDTO criar(OrdemServicoRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Equipamento equipamento = equipamentoRepository.findById(dto.getEquipamentoId())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        OrdemServico os = new OrdemServico();
        os.setCliente(cliente);
        os.setEquipamento(equipamento);
        os.setDescricao(dto.getDescricao());

        os.setDataProximaManutencao(dto.getDataProximaManutencao());

        // Regra de negócio
        os.setDataAbertura(LocalDateTime.now());
        os.setStatus(StatusOrdemServico.ABERTA);


        return toDTO(ordemServicoRepository.save(os));
    }

    // Listar todas
    public List<OrdemServicoResponseDTO> listar() {
        return ordemServicoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Buscar por ID
    public OrdemServicoResponseDTO buscarPorId(Long id) {
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OS não encontrada"));

        return toDTO(os);
    }

    // Atualizar (somente campos permitidos)
    public OrdemServicoResponseDTO atualizar(Long id, OrdemServicoRequestDTO dto) {

        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OS não encontrada"));

        os.setDescricao(dto.getDescricao());
        os.setDataProximaManutencao(dto.getDataProximaManutencao());

        return toDTO(ordemServicoRepository.save(os));
    }

    //  Finalizar OS
    public OrdemServicoResponseDTO finalizar(Long id) {

        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OS não encontrada"));

        if (os.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("OS já está finalizada");
        }

        if (os.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new RuntimeException("OS cancelada não pode ser finalizada");
        }

        os.setStatus(StatusOrdemServico.FINALIZADA);
        os.setDataFechamento(LocalDateTime.now());

        return toDTO(ordemServicoRepository.save(os));
    }

    //  Cancelar OS
    public OrdemServicoResponseDTO cancelar(Long id) {

        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OS não encontrada"));

        if (os.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new RuntimeException("OS finalizada não pode ser cancelada");
        }

        os.setStatus(StatusOrdemServico.CANCELADA);

        return toDTO(ordemServicoRepository.save(os));
    }

    // Listar por OS
    public List<OrdemServicoResponseDTO> buscarPorStatus(StatusOrdemServico status) {

        return ordemServicoRepository.findByStatus(status)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Listar por Cliente
    public List<OrdemServicoResponseDTO> buscarPorCliente(Long clienteId) {

        return ordemServicoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Buscar por Cliente & Status
    public List<OrdemServicoResponseDTO> buscarPorStatusECliente(
            StatusOrdemServico status,
            Long clienteId) {

        return ordemServicoRepository.findByStatusAndClienteId(status, clienteId)
                .stream()
                .map(this::toDTO)
                .toList();
    }



    // Conversão Entity → DTO
    private OrdemServicoResponseDTO toDTO(OrdemServico os) {
        OrdemServicoResponseDTO dto = new OrdemServicoResponseDTO();

        dto.setId(os.getId());
        dto.setDescricao(os.getDescricao());
        dto.setDataAbertura(os.getDataAbertura());
        dto.setDataFechamento(os.getDataFechamento());
        dto.setDataProximaManutencao(os.getDataProximaManutencao());
        dto.setStatus(os.getStatus().name());

        dto.setClienteId(os.getCliente().getId());
        dto.setClienteNome(os.getCliente().getNome());

        dto.setEquipamentoId(os.getEquipamento().getId());
        dto.setEquipamentoNome(os.getEquipamento().getTipo());

        return dto;
    }
}
