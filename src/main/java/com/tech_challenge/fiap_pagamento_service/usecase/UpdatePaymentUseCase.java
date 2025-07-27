package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.dto.UpdatePaymentDTO;

public interface UpdatePaymentUseCase {
    void execute(UpdatePaymentDTO request);
}
