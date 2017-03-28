package it.trade.tradeitapi.utils;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;

import it.trade.tradeitapi.exception.TradeItKeystoreServiceCreateKeyException;
import it.trade.tradeitapi.exception.TradeItKeystoreServiceDecryptException;
import it.trade.tradeitapi.exception.TradeItKeystoreServiceDeleteKeyException;
import it.trade.tradeitapi.exception.TradeItKeystoreServiceEncryptException;

public class TradeItKeystoreService {

    private final String alias;
    private final KeyStore keyStore;
    private static final String keyStoreType = "AndroidKeyStore";

    public TradeItKeystoreService(String alias) throws TradeItKeystoreServiceCreateKeyException {
        this.alias = alias;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null);
        } catch (Exception e) {
            throw new TradeItKeystoreServiceCreateKeyException("Error creating key store with TradeItKeystoreService", e);
        }
    }

    public boolean keyExists() {
        try {
            return keyStore.containsAlias(alias);
        } catch (KeyStoreException e) {
            // this should never happen, as long as keyStore was loaded in the constructor.
            return false;
        }
    }

    public void createKeyIfNotExists(Context context) throws TradeItKeystoreServiceCreateKeyException {
        try {
            if (!keyStore.containsAlias(alias)) {
                long startTime = System.currentTimeMillis();

                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 50);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=TradeIt Link Accounts, O=TradeIt, C=US"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .setKeySize(2048)
                        .build();
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                generator.initialize(spec);
                generator.generateKeyPair();

                Log.d("TRADEIT", "=====> Elapsed time to generate key: " + (System.currentTimeMillis() - startTime) + "ms");
            }
        } catch (Exception e) {
            throw new TradeItKeystoreServiceCreateKeyException("Error creating key with TradeItKeystoreService", e);
        }
    }

    public void deleteKey() throws TradeItKeystoreServiceDeleteKeyException{
        try {
            if (keyStore.containsAlias(alias)) {
                keyStore.deleteEntry(alias);
            }
        } catch (Exception e) {
            throw new TradeItKeystoreServiceDeleteKeyException("Error deleting key with TradeItKeystoreService", e);

        }
    }

    public String encryptString(String stringToEncode) throws TradeItKeystoreServiceEncryptException {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

            Cipher inCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream, inCipher);

            cipherOutputStream.write(stringToEncode.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte [] vals = outputStream.toByteArray();
            return Base64.encodeToString(vals, Base64.DEFAULT);

        } catch (Exception e) {
            throw new TradeItKeystoreServiceEncryptException("Error encrypting the string: "+stringToEncode, e);
        }
    }

    public String decryptString(String stringToDecode) throws TradeItKeystoreServiceDecryptException {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);

            Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());

            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(Base64.decode(stringToDecode, Base64.DEFAULT)), output);

            List<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }
            byte[] bytes = new byte[values.size()];
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }

            return new String(bytes, 0, bytes.length, "UTF-8");

        } catch (Exception e) {
            throw new TradeItKeystoreServiceDecryptException("Error decrypting the string: "+stringToDecode, e);
        }
    }


}
