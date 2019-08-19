package hybrid;

import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AEScryption{

    private byte[] keyValue;

    public AEScryption(String key){
        keyValue = key.getBytes();
    }

    public String encryptAES(String data) throws Exception{
        Key key = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(data.getBytes());
        String encryptedValue = Base64.getEncoder().encodeToString(encVal);
        return encryptedValue;

    }
    public String decryptAES(String encryptedData) throws Exception{
        Key key = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = cipher.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;

    }

    public static String generateKeyAES(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        // System.out.println("KEY: "+saltStr);
        return saltStr;
    }

    public static void main(String args[]){
        
    }
}

