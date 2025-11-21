package io.github.pedrozaz.paymentcore.application.usecase;

import io.github.pedrozaz.paymentcore.application.gateway.WalletGateway;
import io.github.pedrozaz.paymentcore.domain.exception.WalletException;
import io.github.pedrozaz.paymentcore.domain.model.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CreateWalletUseCase {

    private final WalletGateway walletGateway;

    public CreateWalletUseCase(WalletGateway walletGateway) {
        this.walletGateway = walletGateway;
    }

    public Wallet create(UUID userId, String document) {
        if (walletGateway.findByDocument(document).isPresent()) {
            throw new WalletException("Wallet with this document already exists");
        }

        Wallet newWallet = new Wallet(null, userId, document, BigDecimal.ZERO);

        return walletGateway.save(newWallet);
    }
}
