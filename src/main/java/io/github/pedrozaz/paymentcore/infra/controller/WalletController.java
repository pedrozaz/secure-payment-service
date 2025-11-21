package io.github.pedrozaz.paymentcore.infra.controller;

import io.github.pedrozaz.paymentcore.application.usecase.CreateWalletUseCase;
import io.github.pedrozaz.paymentcore.domain.model.Wallet;
import io.github.pedrozaz.paymentcore.infra.controller.dto.CreateWalletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(" api/wallets")
public class WalletController {

    private final CreateWalletUseCase createWalletUseCase;

    public  WalletController(CreateWalletUseCase createWalletUseCase) {
        this.createWalletUseCase = createWalletUseCase;
    }

    @PostMapping
    public ResponseEntity<Wallet> create(@RequestBody @Valid CreateWalletRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Wallet created = createWalletUseCase.create(request.userId(), request.document());

        URI location = uriComponentsBuilder.path("/api/wallets/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
