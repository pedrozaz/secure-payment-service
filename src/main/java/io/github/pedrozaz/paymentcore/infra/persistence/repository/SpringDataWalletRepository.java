package io.github.pedrozaz.paymentcore.infra.persistence.repository;

import io.github.pedrozaz.paymentcore.infra.persistence.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataWalletRepository extends JpaRepository<WalletEntity, UUID> {
    Optional<WalletEntity> findByDocument(String document);
}
