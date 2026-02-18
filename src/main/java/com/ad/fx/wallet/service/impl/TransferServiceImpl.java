package com.ad.fx.wallet.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ad.fx.wallet.dto.TransferRequest;
import com.ad.fx.wallet.dto.TransferResponse;
import com.ad.fx.wallet.enums.TransactionStatus;
import com.ad.fx.wallet.enums.TransactionType;
import com.ad.fx.wallet.exception.InsufficientBalanceException;
import com.ad.fx.wallet.exception.UnauthorisedWalletAccessException;
import com.ad.fx.wallet.exception.UserNotFoundException;
import com.ad.fx.wallet.exception.WalletNotFoundException;
import com.ad.fx.wallet.model.Transaction;
import com.ad.fx.wallet.model.User;
import com.ad.fx.wallet.model.Wallet;
import com.ad.fx.wallet.repository.TransactionRepository;
import com.ad.fx.wallet.repository.UserRepository;
import com.ad.fx.wallet.repository.WalletRepository;
import com.ad.fx.wallet.service.FxRateService;
import com.ad.fx.wallet.service.TransferService;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FxRateService fxRateService;

    @Override
    public TransferResponse transfer(Long userId, TransferRequest request) {

        Wallet fromWallet = walletRepository.findById(request.fromWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Source wallet not found"));

        if (!fromWallet.getUserId().equals(userId)) {
            throw new UnauthorisedWalletAccessException("You don't own this wallet");
        }

        if (fromWallet.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Available: " + fromWallet.getBalance() +
                            ", Required: " + request.amount());
        }

        User recipient = userRepository.findByEmail(request.recipentEmail())
                .orElseThrow(() -> new UserNotFoundException("Recipient not found: " + request.recipentEmail()));

        Wallet toWallet = walletRepository.findByUserIdAndCurrency(recipient.getId(), fromWallet.getCurrency())
                .orElseThrow(() -> new WalletNotFoundException(
                        "Recipient does not have a " + fromWallet.getCurrency() + " wallet"));

        if (fromWallet.getId().equals(toWallet.getId())) {
            throw new IllegalArgumentException("Cannot transfer to same wallet");
        }

        return processSameCurrencyTransfer(fromWallet, toWallet, request);
    }

    private TransferResponse processSameCurrencyTransfer(Wallet fromWallet, Wallet toWallet, TransferRequest request) {
        fromWallet.setBalance(fromWallet.getBalance().subtract(request.amount()));
        walletRepository.save(fromWallet);

        toWallet.setBalance(toWallet.getBalance().add(request.amount()));
        walletRepository.save(toWallet);

        Transaction transaction = new Transaction();
        transaction.setFromWalletId(fromWallet.getId());
        transaction.setToWalletId(toWallet.getId());
        transaction.setAmount(request.amount());
        transaction.setSourceCurrency(fromWallet.getCurrency());
        transaction.setTargetCurrency(toWallet.getCurrency());
        transaction.setFxRate(BigDecimal.ONE);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setTimestamp(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction, request.amount());
    }

    private TransferResponse mapToResponse(Transaction transaction, BigDecimal convertedAmount) {
        return new TransferResponse(
                transaction.getId(),
                transaction.getFromWalletId(),
                transaction.getToWalletId(),
                transaction.getAmount(),
                transaction.getSourceCurrency(),
                transaction.getTargetCurrency(),
                transaction.getFxRate(),
                convertedAmount,
                transaction.getStatus(),
                transaction.getTimestamp());
    }
}