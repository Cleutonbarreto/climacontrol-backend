CREATE TABLE tb_equipamento (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    capacidade INTEGER,
    modelo VARCHAR(100),
    marca VARCHAR(100),
    cliente_id BIGINT NOT NULL,

    CONSTRAINT fk_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES tb_clientes(id)
        ON DELETE CASCADE
);