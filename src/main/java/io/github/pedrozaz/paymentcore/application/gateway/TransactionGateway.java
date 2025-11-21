package io.github.pedrozaz.paymentcore.application.gateway;

import io.github.pedrozaz.paymentcore.domain.model.Transaction;

public interface TransactionGateway {
    Transaction create(Transaction transaction);
    Transaction findByIdempotencyKey(String key);
}
