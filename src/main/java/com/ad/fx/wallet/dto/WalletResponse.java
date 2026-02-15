package com.ad.fx.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ad.fx.wallet.enums.Currency;

public record WalletResponse(Long id, Currency currency, BigDecimal balance, LocalDateTime createdAt) {

}
