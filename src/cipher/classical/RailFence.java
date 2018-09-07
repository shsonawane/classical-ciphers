package cipher.classical;

public class RailFence {

	private int key;
	
	public RailFence(int key) {
		this.key = key;
	}
	
	public String encrypt(String txt){
		int row = key;
		int length = txt.length();
		if(txt.length()%key != 0){
			length += key - (txt.length()%key);
		}
		int col = length/row;
		char [][] mat = new char[row][col];
		int k =0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(k<txt.length()){
					mat[i][j] = txt.charAt(k);
					k++;
				}else{
					mat[i][j] = 'X';
				}
			}
		}
		String cip = "";
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				cip += mat[j][i];
			}
		}
		return cip;	
	}
	
	public String decrypt(String txt){
		int col = key;
		int length = txt.length();
		if(txt.length()%key != 0){
			length += key - (txt.length()%key);
		}
		int row = length/col;
		char [][] mat = new char[row][col];
		int k =0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(k<txt.length()){
					mat[i][j] = txt.charAt(k);
					k++;
				}else{
					mat[i][j] = 'X';
				}
			}
		}
		String cip = "";
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				cip += mat[j][i];
			}
		}
		return cip;		
	}

}
