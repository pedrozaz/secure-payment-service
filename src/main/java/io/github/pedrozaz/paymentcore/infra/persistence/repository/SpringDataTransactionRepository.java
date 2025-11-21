package io.github.pedrozaz.paymentcore.infra.persistence.repository;

import io.github.pedrozaz.paymentcore.infra.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    Optional<TransactionEntity> findByIdempotencyKey(String idempotencyKey);
}
