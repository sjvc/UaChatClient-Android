package es.ua.eps.uachat.util;


import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Esta clase gestiona el cifrado de Strings.
 * Alguien
 */
public class Encryption{
    private static final byte[] SEED = "NoLoDigasANadie.".getBytes();

    public static String encrypt (String str) {
        Key key = new SecretKeySpec(SEED, "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static String decrypt(String str) {
        Key key = new SecretKeySpec(SEED, "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.decode(str.getBytes("UTF-8"), Base64.DEFAULT);

            return new String(cipher.doFinal(decoded), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

}
