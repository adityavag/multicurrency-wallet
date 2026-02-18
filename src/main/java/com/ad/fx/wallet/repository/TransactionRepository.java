package com.ad.fx.wallet.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ad.fx.wallet.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByFromWalletIdInOrToWalletIdIn(List<Long> fromWalletIds,
            List<Long> toWalletIds,
            Pageable pageable);

}
