package com.tech_challenge.fiap_pagamento_service.usecase;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.tech_challenge.fiap_pagamento_service.domain.entity.Pagamento;
import com.tech_challenge.fiap_pagamento_service.dto.CreatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.dto.CreditCardData;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentMethod;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentPublicResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentStatus;

import com.tech_challenge.fiap_pagamento_service.exception.PaymentMethodNotAllowedException;
import com.tech_challenge.fiap_pagamento_service.gateway.CreditCardPaymentGateway;
import com.tech_challenge.fiap_pagamento_service.gateway.PagamentoRepository;

@Component
public class CreatePaymentUseCaseImpl implements CreatePaymentUseCase {
    private final PagamentoRepository pagamentoRepository;
    private final CreditCardPaymentGateway creditCardPayment;

    public CreatePaymentUseCaseImpl(PagamentoRepository pagamentoRepository,
            CreditCardPaymentGateway creditCardPaymentGateway) {
        this.pagamentoRepository = pagamentoRepository;
        this.creditCardPayment = creditCardPaymentGateway;
    }

    @Override
    public PaymentPublicResponseDTO execute(CreatePaymentDTO paymentDTO) {

        var pagamento = Pagamento.builder()
                .valor(paymentDTO.valor())
                .pedidoId(paymentDTO.pedidoId())
                .metodoPagamento(paymentDTO.metodoPagamento())
                .status(PaymentStatus.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        switch (paymentDTO.metodoPagamento()) {
            case PaymentMethod.CARTAO:
                var externalInfo = prepareCreditCardPayment(pagamento, paymentDTO);

                pagamento.setTransacaoIdExterna(externalInfo.id());
                pagamento.setStatus(externalInfo.status());

                var savedPagamento = this.pagamentoRepository.save(pagamento);

                // this.peformRequest(new WebhookRequestDTO(savedPagamento.getId(),
                // savedPagamento.getStatus()));

                return new PaymentPublicResponseDTO(savedPagamento.getId(), savedPagamento.getStatus());
            // break;
            case PaymentMethod.BOLETO:
                throw new PaymentMethodNotAllowedException("Método de pagamento ainda não implementado.");
            // return processBoletoPayment(paymentDTO);
            // break;
            case PaymentMethod.PIX:
                throw new PaymentMethodNotAllowedException("Método de pagamento ainda não implementado.");
            // return processPixPayment(paymentDTO);
            // break;
            default:
                throw new PaymentMethodNotAllowedException("Método de pagamento não suportado");
        }
    }

    private PaymentPublicResponseDTO prepareCreditCardPayment(Pagamento pagamento, CreatePaymentDTO paymentDTO) {
        var creditCardDTO = new CreditCardData(
                paymentDTO.creditCard().creditCardNumber(),
                paymentDTO.creditCard().CVC(),
                paymentDTO.creditCard().ownerName(),
                YearMonth.parse(paymentDTO.creditCard().validTo(), DateTimeFormatter.ofPattern("MM/yy")));

        return new PaymentPublicResponseDTO(
                this.creditCardPayment.processCreditCardPayment(pagamento, creditCardDTO).id(),
                pagamento.getStatus());
    }
}
