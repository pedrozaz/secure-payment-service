package io.github.pedrozaz.paymentcore.infra.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record CreateWalletRequest(
        @NotNull(message = "User ID is required")
        UUID userId,

        @NotBlank(message = "Document is required")
        @CPF(message = "Invalid CPF")
        String document
) {
}
