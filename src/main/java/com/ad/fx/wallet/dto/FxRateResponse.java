package com.ad.fx.wallet.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FxRateResponse {
    @JsonProperty("base")
    private String base;
    @JsonProperty("rates")
    private Map<String, BigDecimal> rates;
}
