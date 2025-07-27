CREATE TABLE pagamento (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    pedido_id VARCHAR(36) NOT NULL,
    valor DECIMAL(19, 2) NOT NULL,
    metodo_pagamento VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP,
    transacao_id_externa VARCHAR(255)
);
