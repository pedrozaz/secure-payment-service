package io.github.pedrozaz.paymentcore.domain.exception;

public class InsufficientBalanceException extends WalletException {
  public InsufficientBalanceException() {
      super("Insufficient balance for this transaction.");
  }
}
