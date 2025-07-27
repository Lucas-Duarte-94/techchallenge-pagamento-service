package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.dto.CreatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentPublicResponseDTO;

public interface CreatePaymentUseCase {
    PaymentPublicResponseDTO execute(CreatePaymentDTO paymentDTO);
}
