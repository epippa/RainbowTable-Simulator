import java.security.NoSuchAlgorithmException;

public class Test2 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String charset = "abcdef0123456789";
        int pwdLength = 4;
        int chainLength = 50;
        int numChains = 2000;
        String hashAlgorithm = "MD5";
        String salt = "de8f329ac8bc62d90ae6bff34ae12bcd";

        RainbowTable2 rainbow = new RainbowTable2(charset, pwdLength, chainLength, numChains, hashAlgorithm, salt);

        System.out.println("Generating Rainbow Table...");
        rainbow.generateTable();

        // Test 1
        String hash1 = "0d0a0795e4df0c716b467d180ea5d95f";
        String result1 = rainbow.lookupTable(hash1);
        System.out.println("Decryption result for hash1: " + result1);

        // Test 2
        String hash2 = "550ca021b146568558b503248a69a6c7";
        String result2 = rainbow.lookupTable(hash2);
        System.out.println("Decryption result for hash2: " + result2);
        
    }
}
