CREATE TABLE wallets (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    document VARCHAR(20) NOT NULL UNIQUE,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_wallets_document ON wallets(document);
CREATE INDEX idx_wallets_user_id ON wallets(user_id);