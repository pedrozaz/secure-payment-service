package io.github.pedrozaz.paymentcore.application.usecase;

import io.github.pedrozaz.paymentcore.application.gateway.WalletGateway;
import io.github.pedrozaz.paymentcore.domain.exception.WalletException;
import io.github.pedrozaz.paymentcore.domain.model.Transaction;
import io.github.pedrozaz.paymentcore.domain.model.TransactionStatus;
import io.github.pedrozaz.paymentcore.domain.model.Wallet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransferUseCase {

    private final WalletGateway walletGateway;

    public  TransferUseCase(WalletGateway walletGateway) {
        this.walletGateway = walletGateway;
    }

    @Transactional
    public Transaction transfer(UUID payerId, UUID payeeId, BigDecimal amount) {
        Wallet payer = walletGateway.findById(payerId)
                .orElseThrow(() -> new WalletException("Payer wallet not found"));

        Wallet payee = walletGateway.findById(payeeId)
                .orElseThrow(() -> new WalletException("Payee wallet not found"));

        payer.debit(amount);
        payee.credit(amount);

        walletGateway.save(payer);
        walletGateway.save(payee);

        return new Transaction(
                UUID.randomUUID(),
                payer.getId(),
                payee.getId(),
                amount,
                TransactionStatus.COMPLETED,
                LocalDateTime.now()
        );
    }
}
