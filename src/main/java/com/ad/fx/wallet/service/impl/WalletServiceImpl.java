package com.ad.fx.wallet.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ad.fx.wallet.dto.CreateWalletRequest;
import com.ad.fx.wallet.dto.WalletResponse;
import com.ad.fx.wallet.exception.WalletAlreadyException;
import com.ad.fx.wallet.model.Wallet;
import com.ad.fx.wallet.repository.WalletRepository;
import com.ad.fx.wallet.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public WalletResponse createWallet(Long userId, CreateWalletRequest request) {
        if (walletRepository.findByUserIdAndCurrency(userId, request.currency()).isPresent()) {
            throw new WalletAlreadyException("Wallet Already Exists For " + request.currency());
        }

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setCurrency(request.currency());
        wallet.setBalance(BigDecimal.ZERO);
        Wallet savedWallet = walletRepository.save(wallet);

        return mapToWalletResponse(savedWallet);
    }

    @Override
    public List<WalletResponse> getAllWalletsForUser(Long userId) {
        List<Wallet> wallets = walletRepository.findByUserId(userId);
        return wallets.stream().map(this::mapToWalletResponse).collect(Collectors.toList());
    }

    @Override
    public WalletResponse getWalletById(Long id, Long userId) {
        Wallet wallet = walletRepository.findById(id)
                .filter(w -> w.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Wallet Not Found"));
        return mapToWalletResponse(wallet);
    }

    private WalletResponse mapToWalletResponse(Wallet wallet) {
        return new WalletResponse(wallet.getId(), wallet.getCurrency(), wallet.getBalance(), wallet.getCreatedAt());
    }
    
}
