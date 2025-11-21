package io.github.pedrozaz.paymentcore.infra.controller.dto;

import io.github.pedrozaz.paymentcore.domain.model.TransactionStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferResponse(
        UUID transactionId,
        TransactionStatus status,
        BigDecimal amount,
        String timestamp
) {
}
