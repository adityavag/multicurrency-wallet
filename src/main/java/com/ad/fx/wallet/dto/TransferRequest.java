package com.ad.fx.wallet.dto;

import java.math.BigDecimal;

public record TransferRequest(
                Long fromWalletId,
                String recipentEmail,
                BigDecimal amount,
                com.ad.fx.wallet.enums.Currency currency) {
}
