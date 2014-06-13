package pgtest.security.service;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.codec.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {

    protected Logger log = Logger.getLogger(PasswordEncryption.class);

    private String algorithm;
    private String encoding;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String encrypt (String plaintext)  {

        MessageDigest msgDigest = null;
        String hashValue = null;
         byte[] digest;
        try {
            msgDigest = MessageDigest.getInstance(algorithm);
            digest = msgDigest.digest(plaintext.getBytes(encoding));

            hashValue = new String((Hex.encode(digest)));
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
        } catch ( UnsupportedEncodingException e) {
            log.error(e);

        }
        return hashValue;
    }
}
