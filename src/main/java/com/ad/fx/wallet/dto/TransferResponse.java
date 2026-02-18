package com.ad.fx.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ad.fx.wallet.enums.Currency;

public record TransferResponse(
        Long transactionId,
        Long fromWalletId,
        Long toWalletId,
        BigDecimal amount,
        Currency sourceCurrency,
        Currency targetCurrency,
        BigDecimal fxRate,
        BigDecimal convertedAmount,
        com.ad.fx.wallet.enums.TransactionStatus status,
        LocalDateTime timestamp) {

}
