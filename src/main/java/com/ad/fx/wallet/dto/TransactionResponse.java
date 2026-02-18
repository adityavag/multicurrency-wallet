package com.ad.fx.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ad.fx.wallet.enums.Currency;
import com.ad.fx.wallet.enums.TransactionStatus;
import com.ad.fx.wallet.enums.TransactionType;

public record TransactionResponse(Long id,
        Long fromWalletId,
        Long toWalletId,
        BigDecimal amount,
        Currency sourceCurrency,
        Currency targetCurrency,
        BigDecimal fxRate,
        TransactionType type,
        TransactionStatus status,
        LocalDateTime timestamp,
        String direction) {

}
