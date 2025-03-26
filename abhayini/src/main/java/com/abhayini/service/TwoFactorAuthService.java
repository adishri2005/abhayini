// Part 2 (for service pkg): Two-Factor Authentication Service
// this service will handle two-factor authentication (2FA) for user login.
// it generates and verifies 6-digit verification codes for user emails.

package com.abhayini.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service // indicates that this class is a service component in the Spring context
public class TwoFactorAuthService
{
    private ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();  // a thread-safe map to store verification codes with email as the key
    public String generateCode(String email)                   // generates a 6-digit verification code for the given email
    {
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(1000000)); // generate a random 6-digit code
        verificationCodes.put(email, code);                   // store the generated code in the map with the email as the key
        System.out.println("Generated 2FA Code for " + email + " is: " + code);      // print the generated code to the console for debugging purposes
        return code;               // return the generated code
    }

     //verifies the provided code against the stored code for the given email
    public boolean verifyCode(String email, String code)
    {

        String storedCode = verificationCodes.get(email);           // retrieve the stored code for the given email
        System.out.println("Verifying 2FA Code for " + email + ": " + code + " (stored: " + storedCode + ")");             // print the verification attempt to the console for debugging purposes
        if (storedCode != null && storedCode.equals(code))                 // check if the stored code matches the provided code
        {
            verificationCodes.remove(email);                    // remove the code from the map after successful verification
            return true;                                      // return true indicating successful verification
        }
        return false;                    // return false indicating failed verification
    }
}