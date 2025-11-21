package io.github.pedrozaz.paymentcore.application.gateway;

import io.github.pedrozaz.paymentcore.domain.model.Wallet;
import io.github.pedrozaz.paymentcore.infra.persistence.entity.WalletEntity;
import io.github.pedrozaz.paymentcore.infra.persistence.repository.SpringDataWalletRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WalletGatewayAdapter implements WalletGateway {

    private final SpringDataWalletRepository repository;

    public WalletGatewayAdapter(SpringDataWalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity entity;
        if (wallet.getId() != null) {
            entity = repository.findById(wallet.getId())
                    .orElseThrow(() -> new RuntimeException("Wallet with id " + wallet.getId() + " not found"));

            entity.setBalance(wallet.getBalance());
        }
        else {
            entity = new WalletEntity(
                    null,
                    wallet.getUserId(),
                    wallet.getDocument(),
                    wallet.getBalance(),
                    null
            );
        }

        WalletEntity saved = repository.save(entity);
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
