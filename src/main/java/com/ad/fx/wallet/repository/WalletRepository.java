package com.ad.fx.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ad.fx.wallet.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
