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

public class GenerateKeyRSA{
    private static final String PRIVATE_KEY_FILE = "Private.key";
    private static final String PUBLIC_KEY_FILE = "Public.key";

    public GenerateKeyRSA(){
         try{
            // System.out.println("GENERATE PRIVATE KEY AND PUBLIC KEY ...");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            // System.out.println("DONE :)");

            // System.out.println("PULLING OUT PARAMETERS WHICH MAKES KEY PAIR ...");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            System.out.println("DONE :)");

            // System.out.println("SAVING PRIVATE KEY AND PUBLIC KEY TO FILES ...");
            GenerateKeyRSA generateKeyRSA = this;
            generateKeyRSA.saveKeys(PUBLIC_KEY_FILE, rsaPublicKeySpec.getModulus(), rsaPublicKeySpec.getPublicExponent());
            generateKeyRSA.saveKeys(PRIVATE_KEY_FILE, rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());
            // System.out.println("DONE :)");
        }catch(Exception e){
            System.out.println("GENERATE_KEY_EXCEPTION ...");
            e.printStackTrace();
        } 
    }

    public static void main(String args[]){
       
    }
    private void saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException{
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try{
            // System.out.println("Generating "+fileName+" ...");
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));
            oos.writeObject(mod);
            oos.writeObject(exp);
            // System.out.println(fileName+" generated and saved SUCCESSFULLY ...");
        }catch(Exception e){
            System.out.println("SAVE_KEY_EXCEPTION ...");
            e.printStackTrace();
        }finally{
            if(oos != null){
                oos.close();
                if(fos != null){
                    fos.close();
                }
            }
        }

    }
}