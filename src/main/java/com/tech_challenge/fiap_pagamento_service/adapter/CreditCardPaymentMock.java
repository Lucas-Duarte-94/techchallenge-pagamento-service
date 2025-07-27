package com.tech_challenge.fiap_pagamento_service.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tech_challenge.fiap_pagamento_service.domain.entity.ExternalPaymentEntity;
import com.tech_challenge.fiap_pagamento_service.domain.entity.Pagamento;
import com.tech_challenge.fiap_pagamento_service.dto.CreditCardData;
import com.tech_challenge.fiap_pagamento_service.dto.ExternalPaymentResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.ExternalPaymentStatus;
import com.tech_challenge.fiap_pagamento_service.gateway.CreditCardPaymentGateway;

@Component
public class CreditCardPaymentMock implements CreditCardPaymentGateway {
    private static final List<String> successCreditCardNumber = List.of("1234-5678-9012-3456");

    private final Logger logger = LoggerFactory.getLogger(CreditCardPaymentMock.class);

    private static List<ExternalPaymentEntity> repository = new ArrayList<ExternalPaymentEntity>();

    private final MockAsyncNotificationService asyncNotificationService;

    public CreditCardPaymentMock(MockAsyncNotificationService asyncNotificationService) {
        this.asyncNotificationService = asyncNotificationService;
    }

    public ExternalPaymentResponseDTO processCreditCardPayment(
            Pagamento pagamento,
            CreditCardData creditCardDTO) {

        logger.debug(pagamento.getPedidoId());

        var status = getStatus(creditCardDTO.creditCardNumber());

        var externalPayment = ExternalPaymentEntity.builder()
                .id(UUID.randomUUID().toString())
                .internalId(pagamento.getId())
                .valor(pagamento.getValor())
                .metodoPagamento(pagamento.getMetodoPagamento())
                .status(status)
                .creditCardData(creditCardDTO)
                .build();

        repository.add(externalPayment);

        logger.debug("Repository data: {}", repository);

        // async
        this.asyncNotificationService.notificarPedidoService(pagamento.getPedidoId(), status);

        return new ExternalPaymentResponseDTO(externalPayment.getId());
    }

    private ExternalPaymentStatus getStatus(String creditCardNumber) {
        if (successCreditCardNumber.contains(creditCardNumber)) {
            return ExternalPaymentStatus.APROVADO;
        }

        return ExternalPaymentStatus.RECUSADO;
    }

}
