package io.github.pedrozaz.paymentcore.application.usecase;

import io.github.pedrozaz.paymentcore.application.gateway.TransactionGateway;
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
    private final TransactionGateway transactionGateway;

    public  TransferUseCase(WalletGateway walletGateway, TransactionGateway transactionGateway) {
        this.walletGateway = walletGateway;
        this.transactionGateway = transactionGateway;
    }

    @Transactional
    public Transaction transfer(UUID payerId, UUID payeeId, BigDecimal amount, String idempotencyKey) {
        Transaction existingTransaction = transactionGateway.findByIdempotencyKey(idempotencyKey);
        if (existingTransaction != null) {
            return  existingTransaction;
        }

        Wallet payer = walletGateway.findById(payerId)
                .orElseThrow(() -> new WalletException("Payer not found"));
        Wallet payee = walletGateway.findById(payeeId)
                .orElseThrow(() -> new WalletException("Payee not found"));

        payer.debit(amount);
        payee.debit(amount);

        walletGateway.save(payer);
        walletGateway.save(payee);

        Transaction newTransaction = new Transaction(
                UUID.randomUUID(),
                payer.getId(),
                payee.getId(),
                amount,
                TransactionStatus.COMPLETED,
                idempotencyKey,
                LocalDateTime.now()
        );

        return transactionGateway.create(newTransaction);
    }
}
