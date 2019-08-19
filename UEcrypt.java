package hybrid;

import java.util.*;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class UEcrypt{

   

    public String encryptUE(String data) throws Exception{
        //Key in STRING form
        String secretKey = AEScryption.generateKeyAES(); //Length should be 16

        AEScryption aes = new AEScryption(secretKey);

        //Data in ENC Form
        String encData1 = aes.encryptAES(data);
        byte[] encData2 = Base64.getDecoder().decode(encData1);

        //Convert Secret Key into ENC Form
        EncryptDataRSA encryptDataRSA = new EncryptDataRSA();
        byte[] encKey2 = encryptDataRSA.encryptData(secretKey);
        String encKey1 = Base64.getEncoder().encodeToString(encKey2);

        //Merge ...
        int len1 = 0;
        len1 = encKey2.length;
        int len2 = 0;
        len2 = encData2.length;

        // System.out.println("Len1: "+len1);
        // System.out.println("Len2: "+len2);

        int len3 = 0;
        len3 = len1+len2;
        // System.out.println("Len3: "+len3);

        byte[] result = new byte[len3];
        System.arraycopy(encKey2, 0, result, 0, len1);
        System.arraycopy(encData2, 0, result, len1, len2);

        String resultStr = Base64.getEncoder().encodeToString(result);
        // System.out.println("Data Original: "+data);
        // System.out.println("Data ENC: "+encData1);
        // System.out.println("Key Original: "+secretKey);
        // System.out.println("key ENC: "+encKey1);
        // System.out.println("Result ENC: "+resultStr);
        


        return resultStr;
    }

    public String decryptUE(String resultStr) throws Exception{
        byte[] result = Base64.getDecoder().decode(resultStr);
        int len1 = 0;
        len1 = 256;
        int len2 = 0;
        len2 = result.length - len1;

        // System.out.println("Len1: "+len1);
        // System.out.println("Len2: "+len2);
        // System.out.println("Len3: "+result.length);

        byte[] encData2 = new byte[len2];
        byte[] encKey2 = new byte[len1];

        System.arraycopy(result, 0, encKey2, 0, len1);
        System.arraycopy(result, len1, encData2, 0, len2);

        String encKey1 = Base64.getEncoder().encodeToString(encKey2);
        String encData1 = Base64.getEncoder().encodeToString(encData2);

        // System.out.println("Data ENC: "+encData1);
        // System.out.println("key ENC: "+encKey1);
        // System.out.println("Result ENC: "+resultStr);

        //Decrypt Secret Key
        DecryptDataRSA decryptDataRSA = new DecryptDataRSA();
        String secretKey = decryptDataRSA.decryptData(encKey2);
        

        // System.out.println("Key Original: "+secretKey);

        AEScryption aes = new AEScryption(secretKey);
        String data = aes.decryptAES(encData1);

        // System.out.println("Data Original: "+data);

        return data;
    }

    public static void main(String args[]){

         try{
            //Generate Keys ...
            GenerateKeyRSA generateKeyRSA = new GenerateKeyRSA();

            //Input ...
            Scanner input = new Scanner(System.in);
            System.out.println("Enter: ");
            String str = input.nextLine();

            UEcrypt ue = new UEcrypt();

            //Encrypt ...
            String encData = ue.encryptUE(str);

            //Decrypt ...
            String data = ue.decryptUE(encData);

            System.out.println("\n\nENC Data:\n"+encData+"\n\n");
            System.out.println("\n\nOriginal Data:"+data+"\n\n");

            
        }catch(Exception e){
            System.out.println("EXCEPTION ...");
            e.printStackTrace();
        }
      

    }
}