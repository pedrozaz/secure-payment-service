package io.github.pedrozaz.paymentcore.application.usecase;

import io.github.pedrozaz.paymentcore.application.gateway.WalletGateway;
import io.github.pedrozaz.paymentcore.domain.exception.WalletException;
import io.github.pedrozaz.paymentcore.domain.model.Wallet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class DepositUseCase {

    private final WalletGateway walletGateway;

    public DepositUseCase(WalletGateway walletGateway) {
        this.walletGateway = walletGateway;
    }

    @Transactional
    public Wallet deposit(UUID walletId, BigDecimal amount) {
        Wallet wallet = walletGateway.findById(walletId)
                .orElseThrow(() -> new WalletException("Wallet not found"));

        wallet.credit(amount);

        return walletGateway.save(wallet);
    }
}
