package com.tech_challenge.fiap_pagamento_service.gateway;

import com.tech_challenge.fiap_pagamento_service.domain.entity.Pagamento;
import com.tech_challenge.fiap_pagamento_service.dto.CreditCardData;
import com.tech_challenge.fiap_pagamento_service.dto.ExternalPaymentResponseDTO;

public interface CreditCardPaymentGateway {
    ExternalPaymentResponseDTO processCreditCardPayment(Pagamento pagamento,
            CreditCardData creditCardDTO);
}
