package io.github.pedrozaz.paymentcore.application.gateway;

import io.github.pedrozaz.paymentcore.domain.model.Wallet;
import io.github.pedrozaz.paymentcore.infra.persistence.entity.WalletEntity;
import io.github.pedrozaz.paymentcore.infra.persistence.repository.SpringDataWalletRepository;

import java.util.Optional;
import java.util.UUID;

public class WalletGatewayAdapter implements WalletGateway {

    private final SpringDataWalletRepository repository;

    public WalletGatewayAdapter(SpringDataWalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity walletEntity = new WalletEntity(
                wallet.getId(),
                wallet.getUserId(),
                wallet.getDocument(),
                wallet.getBalance(),
                null
        );
        WalletEntity saved = repository.save(walletEntity);
        return toDomain(saved);
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<Wallet> findByDocument(String document) {
        return repository.findByDocument(document)
                .map(this::toDomain);
    }

    private Wallet toDomain(WalletEntity e) {
        return new Wallet(
                e.getId(),
                e.getUserId(),
                e.getDocument(),
                e.getBalance()
        );
    }
}
