package com.undecideds.services.generic;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class EncryptionService {
    private static String getKey(){
        return "dWUfCDyr6N";
    }

    public static String HiddenPass(String encrypted){
        StringBuilder sb = new StringBuilder();
        int charCount = Decrypt(encrypted).length();
        for(int i = 0; i < charCount; i++){
            sb.append("*");
        }
        return sb.toString();
    }

    public static String Decrypt(String encrypted){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(getKey());
        return encryptor.decrypt(encrypted.trim());
    }

    public static String Encrypt(String plain){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(getKey());
        return encryptor.encrypt(plain.trim());
    }
}
