package com.ad.fx.wallet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.transaction.TransactionStatus;

import com.ad.fx.wallet.enums.Currency;
import com.ad.fx.wallet.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fromWalletId;
    private Long toWalletId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currency sourceCurrency;
    @Enumerated(EnumType.STRING)
    private Currency targetCurrency;
    private BigDecimal fxRate;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private com.ad.fx.wallet.enums.TransactionStatus status;
}