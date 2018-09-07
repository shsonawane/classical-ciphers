package cipher.classical;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlayFair {
	String key = "";
	char[][] grid = new char[5][5];
	
	public PlayFair(String key) {
		this.key = key;
		createMatrix(key);
	}
	
	private void createMatrix(String key){
		char[] chars = key.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
			if(c == 'J'){
				c = 'I';
			}
		    charSet.add(c);
		}
		Iterator<Character> iterator = charSet.iterator();
		int k =65;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if(iterator.hasNext()){
					char ch= (char) iterator.next();
					grid[i][j] = ch;
				}else{
					while(true){
						if(k == 74){
							k = 75;
						}
						if(!charSet.contains((char)k)){
							grid[i][j] = (char)k;
							k++;
							break;
						}
						k++;
					}
				}
			}
		}
	}
	
	public String encrypt(String plainText){
		String rk = "";
		String ck = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				ck += grid[i][j];
				rk += grid[j][i];
			}
		}
		StringBuffer txt = new StringBuffer(plainText);
		if(txt.length()%2 == 1){
			txt.append('X');
		}
		String cipher = "";
		for (int i = 0; i < txt.length()-1; i+=2) {
			char c1 = txt.charAt(i);
			char c2 = txt.charAt(i+1);
			if(c1 == 'J'){
				c1 = 'I';
			}
			if(c2 == 'J'){
				c1 = 'I';
			}
			if(c1 == c2){
				c2 = 'X';
				txt.insert(i+1, 'X');
			}
			int c1_row = rk.indexOf(c1)%5;
			int c1_col = ck.indexOf(c1)%5;
			int c2_row = rk.indexOf(c2)%5;
			int c2_col = ck.indexOf(c2)%5;
			if(c1_col == c2_col){
				c1_row = (c1_row+1)%5;
				c2_row = (c2_row+1)%5;
				cipher += grid[c1_row][c1_col] +""+ grid[c2_row][c2_col];
			}else if(c1_row == c2_row){
				c1_col = (c1_col+1)%5;
				c2_col = (c2_col+1)%5;
				cipher += grid[c1_row][c1_col] +""+ grid[c2_row][c2_col];
			}else{
				cipher += grid[c1_row][c2_col] +""+ grid[c2_row][c1_col];
			}
		}
		return cipher;
	}
	
	public String decrypt(String cipherText){
		String rk = "";
		String ck = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				ck += grid[i][j];
				rk += grid[j][i];
			}
		}
		StringBuffer txt = new StringBuffer(cipherText);
		String plain = "";
		for (int i = 0; i < txt.length()-1; i+=2) {
			char c1 = txt.charAt(i);
			char c2 = txt.charAt(i+1);
			int c1_row = rk.indexOf(c1)%5;
			int c1_col = ck.indexOf(c1)%5;
			int c2_row = rk.indexOf(c2)%5;
			int c2_col = ck.indexOf(c2)%5;
			if(c1_col == c2_col){
				c1_row = (c1_row+4)%5;
				c2_row = (c2_row+4)%5;
				plain += grid[c1_row][c1_col] +""+ grid[c2_row][c2_col];
			}else if(c1_row == c2_row){
				c1_col = (c1_col+4)%5;
				c2_col = (c2_col+4)%5;
				plain += grid[c1_row][c1_col] +""+ grid[c2_row][c2_col];
			}else{
				plain += grid[c1_row][c2_col] +""+ grid[c2_row][c1_col];
			}
		}
		return plain;
	}
	
}
