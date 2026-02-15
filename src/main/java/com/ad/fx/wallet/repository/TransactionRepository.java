package com.ad.fx.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ad.fx.wallet.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
