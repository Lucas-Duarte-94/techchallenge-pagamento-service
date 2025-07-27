package com.tech_challenge.fiap_pagamento_service.dto;

import java.time.YearMonth;

public record CreditCardData(
                String creditCardNumber,
                String CVC,
                String ownerName,
                YearMonth validTo) {

}
