package io.github.pedrozaz.paymentcore.application.gateway;

import io.github.pedrozaz.paymentcore.domain.model.Transaction;
import io.github.pedrozaz.paymentcore.domain.model.TransactionStatus;
import io.github.pedrozaz.paymentcore.infra.persistence.entity.TransactionEntity;
import io.github.pedrozaz.paymentcore.infra.persistence.repository.SpringDataTransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionGatewayAdapter implements TransactionGateway {

    private final SpringDataTransactionRepository repository;

    public TransactionGatewayAdapter(SpringDataTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction create(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity(
                transaction.id(),
                transaction.payerWalletId(),
                transaction.payeeWalletId(),
                transaction.amount(),
                transaction.status().name(),
                transaction.idempotencyKey(),
                transaction.createdAt()
        );

        TransactionEntity saved = repository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Transaction findByIdempotencyKey(String key) {
        return repository.findByIdempotencyKey(key)
                .map(this::mapToDomain)
                .orElse(null);
    }

    private Transaction mapToDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getPayerWalletId(),
                entity.getPayeeWalletId(),
                entity.getAmount(),
                TransactionStatus.valueOf(entity.getStatus()),
                entity.getIdempotencyKey(),
                entity.getCreatedAt()
        );
    }
}
