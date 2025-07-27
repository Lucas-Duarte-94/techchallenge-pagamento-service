package com.tech_challenge.fiap_pagamento_service.usecase;

import org.springframework.stereotype.Service;

import com.tech_challenge.fiap_pagamento_service.dto.CreatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentPublicResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentWithStatusResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.UpdatePaymentDTO;

@Service
public class PaymentUseCaseImpl implements PaymentUseCase {
    private CreatePaymentUseCase createPaymentUseCase;
    private FindByIdUseCase findByIdUseCase;
    private UpdatePaymentUseCase updatePaymentUseCase;

    public PaymentUseCaseImpl(CreatePaymentUseCase createPaymentUseCase, FindByIdUseCase findByIdUseCase,
            UpdatePaymentUseCase updatePaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.findByIdUseCase = findByIdUseCase;
        this.updatePaymentUseCase = updatePaymentUseCase;
    }

    @Override
    public PaymentPublicResponseDTO createPayment(CreatePaymentDTO paymentDTO) {
        return this.createPaymentUseCase.execute(paymentDTO);
    }

    @Override
    public PaymentWithStatusResponseDTO getById(String id) {
        return this.findByIdUseCase.execute(id);
    }

    @Override
    public void updatePagamento(UpdatePaymentDTO request) {
        this.updatePaymentUseCase.execute(request);
    }

}
