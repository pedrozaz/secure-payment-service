package io.github.pedrozaz.paymentcore.domain.exception;

public class WalletException extends RuntimeException {
    public WalletException(String message) {
        super(message);
    }
}
