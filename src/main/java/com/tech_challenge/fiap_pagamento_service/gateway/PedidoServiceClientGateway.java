package com.tech_challenge.fiap_pagamento_service.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.tech_challenge.fiap_pagamento_service.dto.WebhookRequestDTO;

@FeignClient(name = "pedido-service", url = "${pedido.service.url:http://localhost:8081/webhook}")
public interface PedidoServiceClientGateway {
    @PostMapping
    void handleWebhook(WebhookRequestDTO requestDTO);
}
