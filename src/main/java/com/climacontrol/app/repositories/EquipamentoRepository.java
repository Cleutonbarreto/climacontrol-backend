package com.climacontrol.app.repositories;

import com.climacontrol.app.entities.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    List<Equipamento> findByClienteId(Long clienteId);
}
