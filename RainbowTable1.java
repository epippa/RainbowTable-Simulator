import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.security.*;

// Rainbow Table Without Salt
public class RainbowTable1 {

	private String charset; 
	private int pwdLength;
	private int chainLength;
	private int numChains;
	private HashMap<String, String>  table;
	private String hashAlgorithm;

	//Costruttore
	public RainbowTable1(String charset, int pwdLength, int chainLength, int numChains, String hashAlgorithm) {
		this.charset = charset;
		this.pwdLength = pwdLength;
		this.chainLength = chainLength;
		this.numChains = numChains;
		this.hashAlgorithm = hashAlgorithm;
	}
	
	//generare la tabella
	public void generateTable() throws NoSuchAlgorithmException {
		table = new HashMap<String, String>(numChains);
        String start =""; String end ="";
        while (table.size() < numChains) {
            start = generateRandomPassword(pwdLength);	//generiamo una combinazione (password) random di 4 caratteri
			end = generateChain(start);	//generiamo la catena -> Hash e Reduction
			if (!table.containsKey(end)){
				table.put(end, start);
				System.out.println("Chain no: "+ table.size());	
			}
		}
	}
	
	private String reduce(String digest, int pos) {
		int temp = pos % (digest.length() + 1 - pwdLength);
		String pwd = digest.substring(temp,temp + pwdLength);
		return pwd;
	}

	private String generateChain(String start) throws NoSuchAlgorithmException {
    	String pwd = start;
    	for (int i = 0; i < chainLength; i++){
        	String hash = getDigest(pwd); //hash pwd (starting from start) with java.security to obtain digest
        	pwd = reduce(hash, i); //reduce digest with reduce(String digest, int pos) to obtain pwd
    	}
    	return pwd; //Fine della catena
	}

    public String getDigest(String inputString) throws NoSuchAlgorithmException { //preso da DdigitalSignature project
        MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
        byte[] inputBytes = inputString.getBytes(StandardCharsets.UTF_8); //Garantisce compatibilità cross-platform
        byte[] bytes = md.digest(inputBytes);
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02x", bytes[i] & 0xFF));
        }
        return sb.toString();
    }

    private String generateRandomPassword(int passwordLength) {
        StringBuilder builder = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            builder.append(charset.charAt((int) (Math.random() * charset.length())));
        }
        return builder.toString();
    }

	//interrogare la tabella
	public String lookupTable(String hashToFind) throws NoSuchAlgorithmException {
        for (int i = chainLength - 1; i >= 0; i--) {
			String current = hashToFind.toLowerCase();
            String isDecryptable = null;
            
			//reduce the input hashToFind to obtain pwd
            for (int j = i; j < chainLength; j++) {
                String pwd = reduce(current, j); 
                if (j == chainLength - 1) { 
                    if (table.containsKey(pwd)) { //a possible ending point has been found 
                        isDecryptable = lookupChain(table.get(pwd), hashToFind); //reconstruct the chain starting from the corresponding starting point
                        if (isDecryptable != null) return isDecryptable; //if the hash can be decrypted return otherwise continue
                    }
                } else {
                    current = getDigest(pwd); //hash pwd with java.security
                }
            }
        }
        return "Sorry, I couldn't decrypt this digest :-(";
    }

    private String lookupChain(String start, String hashToFind) throws NoSuchAlgorithmException {
        String pwd = start;
        for (int i = 0; i < chainLength; i++) {
            String digest = getDigest(pwd); //starting from start, hash pwd with java.security to obtain digest
            if (digest.equalsIgnoreCase(hashToFind)) { //if the hash has been found return pwd otherwise continue
                return pwd;
            }
            pwd = reduce(digest, i); //reduce digest with reduce(String digest, int pos) to obtain pwd
        }
        return null;
    }
}
