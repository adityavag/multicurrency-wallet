package com.ad.fx.wallet.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ad.fx.wallet.dto.TransactionResponse;

public interface TransactionService {
    Page<TransactionResponse> getUserTransactions(Long userId, Pageable pageable);

}
