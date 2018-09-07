package cipher.classical;

public class CaesarCipher {
	
	int key;
	
	public CaesarCipher(int key) {
		this.key = key;
	}
	
	public String encrypt(String text) {
        String result = "";
        for (int i=0; i<text.length(); i++){
                char ch = (char)(((int)text.charAt(i) + key - 65) % 26 + 65);
                result += ch;
        }
        return result;
    }
	
	public String decrypt(String text) {
        String result = "";
        for (int i=0; i<text.length(); i++){
                char ch = (char)(((int)text.charAt(i) + (26 - key) - 65) % 26 + 65);
                result += ch;
        }
        return result;
    }

}
