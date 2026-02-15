package com.ad.fx.wallet.service;

import java.util.List;

import com.ad.fx.wallet.dto.CreateWalletRequest;
import com.ad.fx.wallet.dto.DepositRequest;
import com.ad.fx.wallet.dto.WalletResponse;

public interface WalletService {

    WalletResponse createWallet(Long userId, CreateWalletRequest request);

    WalletResponse deposit(Long userId, DepositRequest request);

    List<WalletResponse> getAllWalletsForUser(Long userId);

    WalletResponse getWalletById(Long id, Long userId);

}
