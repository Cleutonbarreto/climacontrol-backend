package com.climacontrol.app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 3, max = 100)
    private String nome;

    //CPF OU CNPJ
    @NotBlank
    @Size(min = 11, max = 14)
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "Documento deve ter 11 ou 14 dígitos")
    private String documento;
    private String telefone;

    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
