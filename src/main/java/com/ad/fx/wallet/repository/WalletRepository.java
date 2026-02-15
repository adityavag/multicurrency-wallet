package com.ad.fx.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ad.fx.wallet.enums.Currency;
import com.ad.fx.wallet.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserIdAndCurrency(Long userId, Currency currency);

    List<Wallet> findByUserId(Long userId);

}
