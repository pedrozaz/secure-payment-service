package io.github.pedrozaz.paymentcore.domain.model;

import io.github.pedrozaz.paymentcore.domain.exception.InsufficientBalanceException;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {

    private final UUID id;
    private final UUID userId;
    private final String document;
    private BigDecimal balance;

    public Wallet(UUID id, UUID userId, String document, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.document = document;
        this.balance = balance;
    }

    public UUID getId() { return id; }
    public BigDecimal getBalance() { return balance; }

    public void debit(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Credit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }
}
