package io.github.pedrozaz.paymentcore.infra.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data @AllArgsConstructor @NoArgsConstructor
public class TransactionEntity {

    @Id
    private UUID id;

    @Column(name = "payer_wallet_id", nullable = false)
    private UUID payerWalletId;

    @Column(name = "payee_wallet_id", nullable = false)
    private UUID payeeWalletId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
