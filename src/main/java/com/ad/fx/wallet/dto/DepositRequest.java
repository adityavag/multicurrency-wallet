package com.ad.fx.wallet.dto;

import java.math.BigDecimal;

public record DepositRequest(Long walletId, BigDecimal amount) {

}
