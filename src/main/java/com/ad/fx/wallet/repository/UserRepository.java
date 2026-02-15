package com.ad.fx.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ad.fx.wallet.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
