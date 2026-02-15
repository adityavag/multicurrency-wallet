package com.ad.fx.wallet.service;

import com.ad.fx.wallet.dto.AuthResponse;
import com.ad.fx.wallet.dto.LoginRequest;
import com.ad.fx.wallet.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}
