package chapter03;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CaesarCipherTest {

    @Test
    public void encrypt() {
        CaesarCipher cipher = new CaesarCipher(3);
        // System.out.println("Encryption code = " + new String(cipher.encoder));
        // System.out.println("Decryption code = " + new String(cipher.decoder));
        Assert.assertEquals("DEFGHIJKLMNOPQRSTUVWXYZABC", new String(cipher.encoder));
        String message = "THE EAGLE IS IN PLAY; MEET AT JOE'S.";
        String coded = cipher.encrypt(message);
        Assert.assertEquals("WKH HDJOH LV LQ SODB; PHHW DW MRH'V.", coded);
    }

    @Test
    public void decrypt() {
        CaesarCipher cipher = new CaesarCipher(3);
        Assert.assertEquals("XYZABCDEFGHIJKLMNOPQRSTUVW", new String(cipher.decoder));
        String coded = "WKH HDJOH LV LQ SODB; PHHW DW MRH'V.";
        String answer = cipher.decrypt(coded);
        Assert.assertEquals("THE EAGLE IS IN PLAY; MEET AT JOE'S.", answer);
    }
}