package com.ad.fx.wallet.dto;

import java.math.BigDecimal;
import java.util.Currency;

public record TransferRequest(
                Long fromWalletId,
                String recipentEmail,
                BigDecimal amount,
                com.ad.fx.wallet.enums.Currency currency) {
}
