package io.github.pedrozaz.paymentcore.application.gateway;

import io.github.pedrozaz.paymentcore.domain.model.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletGateway {
    Wallet save(Wallet wallet);
    Optional<Wallet> findById(UUID id);
    Optional<Wallet> findByDocument(String document);
}
