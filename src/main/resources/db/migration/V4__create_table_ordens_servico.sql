-- V4__create_table_ordens_servico.sql

CREATE TABLE ordens_servico (
    id BIGSERIAL PRIMARY KEY,

    cliente_id BIGINT NOT NULL,
    equipamento_id BIGINT NOT NULL,

    descricao TEXT NOT NULL,

    data_abertura TIMESTAMP NOT NULL,
    data_fechamento TIMESTAMP,

    data_proxima_manutencao DATE,

    status VARCHAR(20) NOT NULL,

    CONSTRAINT fk_ordem_servico_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES tb_clientes (id),

    CONSTRAINT fk_ordem_servico_equipamento
        FOREIGN KEY (equipamento_id)
        REFERENCES tb_equipamento (id)
);