package io.github.pedrozaz.paymentcore.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Data @NoArgsConstructor @AllArgsConstructor
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false)
    private BigDecimal balance;

    @Version
    private Long version;
}
