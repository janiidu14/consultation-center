package com.skinconsultationcenter.w1867160;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class Encryption {
    private static SecretKey key;
    private static Cipher encryptionCipher;

    public Encryption() throws NoSuchAlgorithmException {
        //Generate a key for encryption
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        int KEY_SIZE = 128;
        keyGenerator.init(KEY_SIZE);
        key = keyGenerator.generateKey();
    }

    public String encrypt(String data) throws Exception {
        //Convert Date to byte array
        byte[] dataInBytes = data.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedData) throws Exception {
        //Decode the byte array
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        int DATA_LENGTH = 128;
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    //Method to both encrypt and decrypt image
    public static void encryptImageFile(FileInputStream fis, int key, File file) throws IOException {
        //Convert Image into byte array by creating an array of same size as Image size
        byte[] data = new byte[fis.available()];
        //Read the array
        fis.read(data);

        //Performing an XOR operation to change each value of byte array
        int i = 0;
        for (byte b : data) {
            data[i] = (byte) (b ^ key);
            i++;
        }

        //Opening the file for writing purpose
        FileOutputStream fos = new FileOutputStream(file);

        //Writing new byte array value to image which will Encrypt it
        fos.write(data);

        //Close file
        fos.close();
        fis.close();
    }
}