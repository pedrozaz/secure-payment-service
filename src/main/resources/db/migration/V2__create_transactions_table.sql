CREATE TABLE transactions (
      id UUID PRIMARY KEY,
      payer_wallet_id UUID NOT NULL,
      payee_wallet_id UUID NOT NULL,
      amount DECIMAL(19, 2) NOT NULL,
      status VARCHAR(20) NOT NULL,
      idempotency_key VARCHAR(255) NOT NULL,
      created_at TIMESTAMP NOT NULL,
      CONSTRAINT uc_transactions_idempotency_key UNIQUE (idempotency_key)
    );

ALTER TABLE transactions ADD CONSTRAINT fk_transactions_payer
    FOREIGN KEY (payer_wallet_id) REFERENCES wallets(id);

ALTER TABLE transactions ADD CONSTRAINT fk_transactions_payee
    FOREIGN KEY (payee_wallet_id) REFERENCES wallets(id);