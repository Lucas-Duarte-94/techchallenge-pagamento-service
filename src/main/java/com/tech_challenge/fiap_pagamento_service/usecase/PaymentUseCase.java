package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.dto.CreatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentPublicResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentWithStatusResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.UpdatePaymentDTO;

public interface PaymentUseCase {
    PaymentPublicResponseDTO createPayment(CreatePaymentDTO paymentDTO);

    PaymentWithStatusResponseDTO getById(String id);

    void updatePagamento(UpdatePaymentDTO request);
}
