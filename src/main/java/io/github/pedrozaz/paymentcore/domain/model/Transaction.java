package io.github.pedrozaz.paymentcore.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(
        UUID id,
        UUID payerWalletId,
        UUID payeeWalletId,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createdAt
) {
}
