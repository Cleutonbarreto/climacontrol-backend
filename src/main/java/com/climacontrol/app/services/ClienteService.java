package com.climacontrol.app.services;

import com.climacontrol.app.dto.ClienteRequestDTO;
import com.climacontrol.app.dto.ClienteResponseDTO;
import com.climacontrol.app.entities.Cliente;
import com.climacontrol.app.exceptions.DocumentoInvalidoException;
import com.climacontrol.app.exceptions.DocumentoJaCadastradoException;
import com.climacontrol.app.repositories.ClienteRepository;
import com.climacontrol.app.utils.DocumentoValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ClienteResponseDTO criar(ClienteRequestDTO dto) {

        // 1. Normalizar documento
        String documento = dto.getDocumento().replaceAll("\\D", "");

        // 2. Validar documento
        if (!DocumentoValidator.isValido(documento)) {
            throw new DocumentoInvalidoException("CPF/CNPJ inválido.");
        }

        // 3. Verificar duplicidade
        if (repository.findByDocumento(documento).isPresent()) {
            throw new DocumentoJaCadastradoException("Documento já cadastrado.");
        }

        // 4. Criar entidade
        Cliente cliente = new Cliente();

        cliente.setNome(dto.getNome().trim());
        cliente.setDocumento(documento);
        cliente.setTelefone(dto.getTelefone());

        cliente.setCep(dto.getCep());
        cliente.setRua(dto.getRua());
        cliente.setNumero(dto.getNumero());
        cliente.setBairro(dto.getBairro());
        cliente.setCidade(dto.getCidade());
        cliente.setEstado(dto.getEstado());

        // 5. Salvar
        cliente = repository.save(cliente);

        return mapToResponse(cliente);
    }


    // Listar todos os clientes
    public List<ClienteResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new  RuntimeException("Cliente não encontrado"));
        return mapToResponse(cliente);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {

        // 1. Buscar cliente
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // 2. Normalizar documento
        String documento = dto.getDocumento().replaceAll("\\D", "");

        // 3. Validar documento
        if (!DocumentoValidator.isValido(documento)) {
            throw new DocumentoInvalidoException("CPF/CNPJ inválido.");
        }

        // 4. Verificar duplicidade (ignorando o próprio cliente)
        if (repository.findByDocumento(documento)
                .filter(c -> !c.getId().equals(id))
                .isPresent()) {

            throw new DocumentoJaCadastradoException("Documento já cadastrado.");
        }

        // 5. Atualizar dados
        cliente.setNome(dto.getNome().trim()); // ✔ alinhado com criar
        cliente.setDocumento(documento);
        cliente.setTelefone(dto.getTelefone());

        cliente.setCep(dto.getCep());
        cliente.setRua(dto.getRua());
        cliente.setNumero(dto.getNumero());
        cliente.setBairro(dto.getBairro());
        cliente.setCidade(dto.getCidade());
        cliente.setEstado(dto.getEstado());

        // 6. Salvar
        cliente = repository.save(cliente);

        return mapToResponse(cliente);
    }

    // Deletar
    public void deletar (Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        repository.delete(cliente);
    }



    private void  mapToEntity(ClienteRequestDTO dto, Cliente cliente) {
        cliente.setNome(dto.getNome());
        cliente.setDocumento(dto.getDocumento());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCep(dto.getCep());
        cliente.setRua(dto.getRua());
        cliente.setNumero(dto.getNumero());
        cliente.setBairro(dto.getBairro());
        cliente.setCidade(dto.getCidade());
        cliente.setEstado(dto.getEstado());
    }

    private ClienteResponseDTO mapToResponse (Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getDocumento(),
                cliente.getTelefone(),
                cliente.getCep(),
                cliente.getRua(),
                cliente.getNumero(),
                cliente.getBairro(),
                cliente.getCidade(),
                cliente.getEstado()
        );
    }
}
