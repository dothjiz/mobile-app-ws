package com.doth.app.ws.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    public String generateUserId(int length){
        return generateRandomString(length);
    }

    public String generateAddressId(int lenght){
        return generateRandomString(lenght);
    }

    private String generateRandomString(int lenght){
        StringBuilder returnValue = new StringBuilder(lenght);
        for (int i=0; i < lenght; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}
