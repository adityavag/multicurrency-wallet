package com.ad.fx.wallet.service.impl;

import com.ad.fx.wallet.model.Transaction;
import com.ad.fx.wallet.model.Wallet;
import com.ad.fx.wallet.repository.TransactionRepository;
import com.ad.fx.wallet.repository.WalletRepository;
import com.ad.fx.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Page<com.ad.fx.wallet.dto.TransactionResponse> getUserTransactions(Long userId, Pageable pageable) {
        List<Long> walletIds = walletRepository.findByUserId(userId)
                .stream()
                .map(Wallet::getId)
                .collect(Collectors.toList());

        Page<Transaction> transactions = transactionRepository
                .findByFromWalletIdInOrToWalletIdIn(walletIds, walletIds, pageable);

        return transactions.map(transaction -> mapToResponse(transaction, walletIds));
    }

    private com.ad.fx.wallet.dto.TransactionResponse mapToResponse(Transaction transaction, List<Long> userWalletIds) {
        String direction;
        if (transaction.getFromWalletId() != null &&
                userWalletIds.contains(transaction.getFromWalletId())) {
            direction = "SENT";
        } else {
            direction = "RECEIVED";
        }

        return new com.ad.fx.wallet.dto.TransactionResponse(
                transaction.getId(),
                transaction.getFromWalletId(),
                transaction.getToWalletId(),
                transaction.getAmount(),
                transaction.getSourceCurrency(),
                transaction.getTargetCurrency(),
                transaction.getFxRate(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getTimestamp(),
                direction);
    }
}