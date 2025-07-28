
package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.dto.CreatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentPublicResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentStatus;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentWithStatusResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.UpdatePaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentUseCaseImplTest {

    @Mock
    private CreatePaymentUseCase createPaymentUseCase;

    @Mock
    private FindByIdUseCase findByIdUseCase;

    @Mock
    private UpdatePaymentUseCase updatePaymentUseCase;

    @InjectMocks
    private PaymentUseCaseImpl paymentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment() {
        CreatePaymentDTO createPaymentDTO = new CreatePaymentDTO(null, null, null, null);
        PaymentPublicResponseDTO expectedResponse = new PaymentPublicResponseDTO("1", PaymentStatus.PENDENTE);

        when(createPaymentUseCase.execute(createPaymentDTO)).thenReturn(expectedResponse);

        PaymentPublicResponseDTO actualResponse = paymentUseCase.createPayment(createPaymentDTO);

        verify(createPaymentUseCase).execute(createPaymentDTO);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetById() {
        String id = "123";
        PaymentWithStatusResponseDTO expectedResponse = new PaymentWithStatusResponseDTO(id, PaymentStatus.APROVADO);

        when(findByIdUseCase.execute(id)).thenReturn(expectedResponse);

        PaymentWithStatusResponseDTO actualResponse = paymentUseCase.getById(id);

        verify(findByIdUseCase).execute(id);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testUpdatePagamento() {
        UpdatePaymentDTO updatePaymentDTO = new UpdatePaymentDTO("order123", PaymentStatus.APROVADO);

        paymentUseCase.updatePagamento(updatePaymentDTO);

        verify(updatePaymentUseCase).execute(updatePaymentDTO);
    }
}
