package com.ad.fx.wallet.service;

import java.math.BigDecimal;
import java.util.Map;

import com.ad.fx.wallet.enums.Currency;

public interface FxRateService {
    BigDecimal getRate(Currency from, Currency to);
    Map<Currency, BigDecimal> getAllRates(Currency base);
}
