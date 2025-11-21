package io.github.pedrozaz.paymentcore.infra.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull(message = "Payer ID is required")
        UUID payerId,

        @NotNull(message = "Payee ID is required")
        UUID payeeId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount
) {
}
