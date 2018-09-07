package cipher.classical;

public class OneTimePad {
	
	String key;
	
	public OneTimePad(String key) {
		this.key = key;
	}
	
	public String encrypt(String text) {
        String result = "";
        int l = 0;
        for (int i=0; i<text.length(); i++){
        		if(l >= key.length() ){
            		l=0;
            	}
                char ch = (char)(((int)text.charAt(i) - 65 + (int)key.charAt(l) - 65) % 26 + 65);
                result += ch;
                l++;
        }
        return result;
    }
	
	String decrypt(String text) {
        String result = "";
        int l=0;
        for (int i=0; i<text.length(); i++){
        		if(l >= key.length() ){
        			l=0;
        		}
                char ch = (char)(((int)text.charAt(i) - 65 + (26 - ((int)key.charAt(l) - 65))) % 26 + 65);
                result += ch;
                l++;
        }
        return result;
    }

}
