package hybrid;

import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptDataRSA{

    private static final String PRIVATE_KEY_FILE = "Private.key";
    private static final String PUBLIC_KEY_FILE = "Public.key";

    public static void main(String args[]){
        
        
    }

 
    public byte[] encryptData(String data) throws IOException{
        // System.out.println("ENCRYPTION STARTED ...");
        // System.out.println("\n\nData Before Encryption ::: \n"+data+"\n\n");
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;

        try{
            PublicKey publicKey = readPublicKeyFromFile(this.PUBLIC_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedData = cipher.doFinal(dataToEncrypt);

            // System.out.println("\n\nEncrypted Data ::: \nLENGTH: "+(encryptedData.length)+"\n"+ new String(encryptedData)+"\n\n");
            


        }catch(Exception e){
            System.out.println("ENCRYPT_DATA_EXCEPTION ...");
            e.printStackTrace();
        }
        // System.out.println("ENCRYPTION COMPLETED ...");
        return encryptedData;

    }

    public PublicKey readPublicKeyFromFile(String fileName) throws IOException{
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger mod = (BigInteger)ois.readObject();
            BigInteger exp = (BigInteger)ois.readObject();
            //Get Public Key
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(mod, exp);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
            return publicKey;
        }catch(Exception e){
            System.out.println("READ_PUBLIC_KEY_FROM_FILE_EXCEPTION...");
            e.printStackTrace();
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