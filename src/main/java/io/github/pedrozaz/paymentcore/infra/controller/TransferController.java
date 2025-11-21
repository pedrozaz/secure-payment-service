package io.github.pedrozaz.paymentcore.infra.controller;

import io.github.pedrozaz.paymentcore.application.usecase.TransferUseCase;
import io.github.pedrozaz.paymentcore.domain.model.Transaction;
import io.github.pedrozaz.paymentcore.infra.controller.dto.TransferRequest;
import io.github.pedrozaz.paymentcore.infra.controller.dto.TransferResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferUseCase transferUseCase;

    public TransferController(TransferUseCase transferUseCase) {
        this.transferUseCase = transferUseCase;
    }

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(
            @RequestBody @Valid TransferRequest request,
            @RequestHeader("Idempotency-Key") String idempotencyKey) {

        Transaction transaction = transferUseCase.transfer(
                request.payerId(),
                request.payeeId(),
                request.amount(),
                idempotencyKey
        );

        TransferResponse response = new TransferResponse(
                transaction.id(),
                transaction.status(),
                transaction.amount(),
                transaction.createdAt().toString()
        );

        return ResponseEntity.ok(response);
    }
}
