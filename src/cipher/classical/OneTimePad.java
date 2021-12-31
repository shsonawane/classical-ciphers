package cipher.classical;

public class OneTimePad {
	
	String key;
	
	public OneTimePad(String key) {
		this.key = key;
	}
	
	public String encrypt(String text) {
        String result = "";
        if(text.length() > key.length()){
        	for (int i = key.length(); i < text.length(); i++) {
				key += "X";
			}
        }
        for (int i=0; i<text.length(); i++){
                char ch = (char)(((int)text.charAt(i) - 65 + (int)key.charAt(i) - 65) % 26 + 65);
                result += ch;
        }
        return result;
    }
	
	public String decrypt(String text) {
        String result = "";
        if(text.length() > key.length()){
        	for (int i = key.length(); i < text.length(); i++) {
				key += "X";
			}
        }
        for (int i=0; i<text.length(); i++){
                char ch = (char)(((int)text.charAt(i) - 65 + (26 - ((int)key.charAt(i) - 65))) % 26 + 65);
                result += ch;
        }
        return result;
    }

}
