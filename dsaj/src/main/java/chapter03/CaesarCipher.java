package chapter03;

public class CaesarCipher {
    protected char[] encoder = new char[26];
    protected char[] decoder = new char[26];

    public CaesarCipher(int rotation) {
        for (int i = 0; i < 26; i++) {
            encoder[i] = (char) ('A' + (i + rotation) % 26);
            decoder[i] = (char) ('A' + (i + 26 - rotation) % 26);
        }
    }

    public String encrypt(String message) {
        return transform(message, encoder);
    }

    public String decrypt(String secret) {
        return transform(secret, decoder);
    }

    private String transform(String message, char[] encoder) {
        char[] msg = message.toCharArray();
        for (int i = 0; i < msg.length; i++) {
            if (Character.isUpperCase(msg[i])) {
                int j = msg[i] - 'A';
                msg[i] = encoder[j];
            }
        }
        return new String(msg);
    }

//    public static void main(String[] args) {
//        CaesarCipher cipher = new CaesarCipher(3);
//        System.out.println("Encryption code = " + new String(cipher.encoder));
//        System.out.println("Decryption code = " + new String(cipher.decoder));
//        String message = "THE EAGLE IS IN PLAY; MEET AT JOE'S.";
//        String coded = cipher.encrypt(message);
//        System.out.println("Secret: " + coded);
//        String answer = cipher.decrypt(coded);
//        System.out.println("Message: " + answer);
//    }
}
