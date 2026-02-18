package com.ad.fx.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ad.fx.wallet.dto.TransferResponse;
import com.ad.fx.wallet.model.User;
import com.ad.fx.wallet.repository.UserRepository;
import com.ad.fx.wallet.service.TransferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody com.ad.fx.wallet.dto.TransferRequest request) {
        Long userId = getCurrentUserId();
        TransferResponse response = transferService.transfer(userId, request);
        return ResponseEntity.ok(response);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return user.getId();
    }
}
