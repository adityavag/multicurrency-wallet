package com.ad.fx.wallet.controller;

import com.ad.fx.wallet.enums.Currency;
import com.ad.fx.wallet.service.FxRateService;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/fx-rates")
public class FxRateController {

    @Autowired
    private FxRateService fxRateService;

    @GetMapping("/current")
    public ResponseEntity<Map<Currency, BigDecimal>> getCurrentRates(@RequestParam Currency base) {
        Map<Currency, BigDecimal> rates = fxRateService.getAllRates(base);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(
            @RequestParam Currency from,
            @RequestParam Currency to,
            @RequestParam BigDecimal amount) {

        BigDecimal rate = fxRateService.getRate(from, to);
        BigDecimal convertedAmount = amount.multiply(rate);

        ConversionResponse response = new ConversionResponse(from, to, amount, rate, convertedAmount);
        return ResponseEntity.ok(response);
    }

    public static class ConversionResponse {
        private Currency from;
        private Currency to;
        private BigDecimal amount;
        private BigDecimal rate;
        private BigDecimal convertedAmount;

        public ConversionResponse(Currency from, Currency to, BigDecimal amount, BigDecimal rate,
                BigDecimal convertedAmount) {
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.rate = rate;
            this.convertedAmount = convertedAmount;
        }

        // Getters
        public Currency getFrom() {
            return from;
        }

        public Currency getTo() {
            return to;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public BigDecimal getConvertedAmount() {
            return convertedAmount;
        }
    }
}