package com.ad.fx.wallet.service.impl;

import com.ad.fx.wallet.dto.FxRateResponse;
import com.ad.fx.wallet.enums.Currency;
import com.ad.fx.wallet.service.FxRateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FxRateServiceImpl implements FxRateService {

    @Value("${fx.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // In-memory cache to reduce API calls
    private final Map<String, BigDecimal> rateCache = new ConcurrentHashMap<>();

    @Override
    public BigDecimal getRate(Currency from, Currency to) {
        // If same currency, rate is 1
        if (from == to) {
            return BigDecimal.ONE;
        }

        // Check cache first
        String cacheKey = from + "_" + to;
        if (rateCache.containsKey(cacheKey)) {
            return rateCache.get(cacheKey);
        }

        // Fetch from API
        try {
            String url = apiUrl + from.name();
            FxRateResponse response = restTemplate.getForObject(url, FxRateResponse.class);

            if (response != null && response.getRates() != null) {
                BigDecimal rate = response.getRates().get(to.name());

                if (rate != null) {
                    // Cache the rate
                    rateCache.put(cacheKey, rate);
                    return rate;
                }
            }

            throw new RuntimeException("Unable to fetch exchange rate for " + from + " to " + to);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching FX rates: " + e.getMessage());
        }
    }

    @Override
    public Map<Currency, BigDecimal> getAllRates(Currency base) {
        Map<Currency, BigDecimal> rates = new HashMap<>();

        try {
            String url = apiUrl + base.name();
            FxRateResponse response = restTemplate.getForObject(url, FxRateResponse.class);

            if (response != null && response.getRates() != null) {
                for (Currency currency : Currency.values()) {
                    if (currency != base) {
                        BigDecimal rate = response.getRates().get(currency.name());
                        if (rate != null) {
                            rates.put(currency, rate);
                        }
                    }
                }
            }

            return rates;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching FX rates: " + e.getMessage());
        }
    }
}
