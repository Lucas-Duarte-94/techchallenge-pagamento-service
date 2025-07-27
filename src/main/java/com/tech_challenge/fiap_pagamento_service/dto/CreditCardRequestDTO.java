package com.tech_challenge.fiap_pagamento_service.dto;

public record CreditCardRequestDTO(
                String creditCardNumber,
                String CVC,
                String ownerName,
                String validTo) {

}