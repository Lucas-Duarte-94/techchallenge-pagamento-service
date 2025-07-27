package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.dto.PaymentWithStatusResponseDTO;

public interface FindByIdUseCase {
    PaymentWithStatusResponseDTO execute(String id);
}
