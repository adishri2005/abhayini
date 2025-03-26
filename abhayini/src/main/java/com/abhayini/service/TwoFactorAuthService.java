package com.abhayini.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TwoFactorAuthService {
    private ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();

    public String generateCode(String email) {
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
        verificationCodes.put(email, code);
        System.out.println("Generated 2FA Code for " + email + " is: " + code);
        return code;
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        System.out.println("Verifying 2FA Code for " + email + ": " + code + " (stored: " + storedCode + ")");
        if (storedCode != null && storedCode.equals(code)) {
            verificationCodes.remove(email);
            return true;
        }
        return false;
    }
}