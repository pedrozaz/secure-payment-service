package io.github.pedrozaz.paymentcore.infra.controller;

import io.github.pedrozaz.paymentcore.application.usecase.CreateWalletUseCase;
import io.github.pedrozaz.paymentcore.application.usecase.DepositUseCase;
import io.github.pedrozaz.paymentcore.domain.model.Wallet;
import io.github.pedrozaz.paymentcore.infra.controller.dto.CreateWalletRequest;
import io.github.pedrozaz.paymentcore.infra.controller.dto.DepositRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/wallets")
public class WalletController {

    private final CreateWalletUseCase createWalletUseCase;
    private final DepositUseCase depositUseCase;

    public  WalletController(CreateWalletUseCase createWalletUseCase, DepositUseCase depositUseCase) {
        this.createWalletUseCase = createWalletUseCase;
        this.depositUseCase = depositUseCase;
    }

    @PostMapping
    public ResponseEntity<Wallet> create(@RequestBody @Valid CreateWalletRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Wallet created = createWalletUseCase.create(request.userId(), request.document());

        URI location = uriComponentsBuilder.path("/api/wallets/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Wallet> deposit(@PathVariable UUID id, @RequestBody @Valid DepositRequest request) {
        Wallet updateWallet = depositUseCase.deposit(id, request.amount());
        return ResponseEntity.ok(updateWallet);
    }
}
