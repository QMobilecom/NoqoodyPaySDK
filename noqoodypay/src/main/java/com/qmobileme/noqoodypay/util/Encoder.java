package com.qmobileme.noqoodypay.util;

import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Saneeb Salam
 * on 28/06/2021.
 */
public class Encoder {

    public String sha256String(String source) {
        byte[] hash = null;
        String hashCode = null;// w  ww  .  j  a va 2 s.c  o m
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(source.getBytes());
        } catch (NoSuchAlgorithmException e) {
            Log.wtf("SHA-256", "Can't calculate SHA-256");
        }

        if (hash != null) {
            StringBuilder hashBuilder = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(hash[i]);
                if (hex.length() == 1) {
                    hashBuilder.append("0");
                    hashBuilder.append(hex.charAt(hex.length() - 1));
                } else {
                    hashBuilder.append(hex.substring(hex.length() - 2));
                }
            }
            hashCode = hashBuilder.toString();
        }
        return hashCode;
    }

    public String HmacSHA256(String source, String ClientSecret) {
        String hash = "";
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(ClientSecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            hash = new String(Base64.encodeBase64(sha256_HMAC.doFinal(source.getBytes())));
//            hash = org.apache.commons.codec.binary.Base64.encodeBase64String(sha256_HMAC.doFinal(source.getBytes()));
        } catch (Exception ignored) {
        }
        return hash;
    }


}
