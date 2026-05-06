package com.climacontrol.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_equipamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    private String tipo;

    private Integer capacidade;

    private String modelo;

    private String marca;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
