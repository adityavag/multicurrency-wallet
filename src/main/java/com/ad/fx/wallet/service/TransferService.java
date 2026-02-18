package com.ad.fx.wallet.service;

import com.ad.fx.wallet.dto.TransferRequest;
import com.ad.fx.wallet.dto.TransferResponse;

public interface TransferService {
    TransferResponse transfer(Long userId, TransferRequest request);
}
