package com.climacontrol.app.repositories;

import com.climacontrol.app.entities.OrdemServico;
import com.climacontrol.app.enums.StatusOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    List<OrdemServico> findByStatus(StatusOrdemServico status);
    List<OrdemServico> findByClienteId(Long clienteId);
    List<OrdemServico> findByStatusAndClienteId(StatusOrdemServico status, Long clienteId);

}
