// Part 2 (for service pkg): Two-Factor Authentication Service
// this service will handle two-factor authentication (2FA) for user login.
// it generates and verifies 6-digit verification codes for user emails.

package com.abhayini.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TwoFactorAuthService {
    // In-memory storage for verification codes keyed by email.
    private ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();

    // Generates and stores a 6-digit verification code for the given email.
    public String generateCode(String email) {
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
        verificationCodes.put(email, code);
        // Simulate sending code (e.g., via email/SMS) by printing it.
        System.out.println("2FA Code for " + email + " is: " + code);
        return code;
    }

    // Verifies that the code provided matches the stored code.
    public boolean verifyCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            verificationCodes.remove(email); // Invalidate the code after successful verification.
            return true;
        }
        return false;
    }
}
