package hybrid;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DecryptDataRSA{

    private static final String PRIVATE_KEY_FILE = "Private.key";
    private static final String PUBLIC_KEY_FILE = "Public.key";
 

    public static void main(String args[]){


    }
    
    public String decryptData(byte[] data) throws IOException{
        // System.out.println("DECRYPTION STARTED ...");
        byte[] decryptedData = null;
        try{
            PrivateKey privateKey = readPrivateKeyFromFile(this.PRIVATE_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedData = cipher.doFinal(data);
            String decryptedDataStr = new String(decryptedData);
            return decryptedDataStr;
            // System.out.println("\n\nDecrypted Data ::: \n"+ new String(decryptedData)+"\n\n");
        }catch(Exception e){
            System.out.println("DECRYPT_DATA_EXCEPTION: "+e.getMessage());
        }
        // System.out.println("DECRYPTION COMPLETED ...");
        return null;
    }

    public PrivateKey readPrivateKeyFromFile(String fileName) throws IOException{
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger mod = (BigInteger)ois.readObject();
            BigInteger exp = (BigInteger)ois.readObject();
            //Get Private Key
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(mod, exp);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
            return privateKey;
        }catch(Exception e){
            System.out.println("READ_PRIVATE_KEY_FROM_FILE_EXCEPTION: "+e.getMessage());
        }finally{
            if(ois != null){
                ois.close();
                if(fis != null){
                    fis.close();
                }
            }
        }
        return null;
    }
}