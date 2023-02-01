package com.undecideds.services.generic;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class EncryptionService {
    private static String getKey(){
        return "dWUfCDyr6N";
    }

    public static String Decrypt(String encrypted){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(getKey());
        return encryptor.decrypt(encrypted.trim());
    }
}
