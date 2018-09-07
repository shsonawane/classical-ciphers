package cipher.classical;
import java.util.Random;

import Jama.*;

public class HillCipher {
	
	private String key;
	private int n;
	private double keyMatrix[][];
	private double keyMatrixInverse[][];
	
	public HillCipher(String key) {
		double sr = Math.sqrt(key.length());
	    if((sr - Math.floor(sr)) != 0){
	    	  throw new IllegalStateException("Length of key must be a perfect square");	  
	    }
	    n = (int)sr;
		this.key = key;
    	keyMatrix = new double[n][n];
	    int k = 0;
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++){
	            keyMatrix[i][j] = (key.charAt(k)) % 65;
	            k++;
	        }
	    }
	    Matrix mat = new Matrix(keyMatrix);
		int det = (int)mat.det() % 26;
		if(det < 0)
			det += 26;
	    int din = -1;
	    for(int i = 0; i< 26;i++){
			if((det*i)%26 == 1){
				din = i;
				break;
			}
		}
	    if(din == -1){
	    	throw new IllegalStateException("NOT_INVERSIBLE MOD 26, Try different key");	
	    }
	    double d = mat.det();
		mat = mat.inverse();
		keyMatrixInverse = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				keyMatrixInverse[i][j] = Math.round(mat.get(i, j)*d*din)%26;
				if(keyMatrixInverse[i][j] < 0 ){
					keyMatrixInverse[i][j] += 26;
				}
				//System.out.print(keyMatrixInverse[i][j]+" ");
			}
			//System.out.println();
		}
	}
	
    public String getKey(){
    	return key;
    }
	 
	private int[][] encryptBlock(int messageVector[][],int n){
	    int x, i, j;
	    int cipherMatrix[][] = new int[n][1];
	    for (i = 0; i < n; i++)  {
	        for (j = 0; j < 1; j++) {
	            cipherMatrix[i][j] = 0;
	          
	             for (x = 0; x < n; x++)  {
	                cipherMatrix[i][j] += 
	                     keyMatrix[i][x] * messageVector[x][j];
	            }
	         
	            cipherMatrix[i][j] = cipherMatrix[i][j] % 26;
	        }
	    }
		return cipherMatrix;
	}
	
	private int[][] decryptBlock(int messageVector[][],int n){
	    int x, i, j;
	    int cipherMatrix[][] = new int[n][1];
	    for (i = 0; i < n; i++)  {
	        for (j = 0; j < 1; j++) {
	            cipherMatrix[i][j] = 0;
	          
	             for (x = 0; x < n; x++)  {
	                cipherMatrix[i][j] += 
	                     keyMatrixInverse[i][x] * messageVector[x][j];
	            }
	         
	            cipherMatrix[i][j] = cipherMatrix[i][j] % 26;
	        }
	    }
		return cipherMatrix;
	}
	 
	public String encrypt(String msg){
	    String CipherText = "";
	    while(msg.length() % n != 0){
        	msg += "X";
        }
	    int u=0,v=n;
	    while(v <= msg.length()){
	    	String txt = msg.substring(u, v);
	    	u += n;
	    	v += n;
	 	 
	 	    int messageVector[][] = new int[n][1];
	 	    for (int i = 0; i < n; i++)
	 	        messageVector[i][0] = (txt.charAt(i)) % 65;
	 	 
	 	    int cipherMatrix[][] = encryptBlock(messageVector,n);
	 	
	 	    for (int i = 0; i < n; i++){
	 	    	char ch = (char)(cipherMatrix[i][0] + 65);
	 	        CipherText += ch;
	 	    }
	    }
	    

	    return CipherText;
	}
	
	public String decrypt(String msg){
	    String CipherText = "";
	    while(msg.length() % n != 0){
        	msg += "X";
        }
	    int u=0,v=n;
	    while(v <= msg.length()){
	    	String txt = msg.substring(u, v);
	    	u += n;
	    	v += n;
	 	 
	 	    int messageVector[][] = new int[n][1];
	 	    for (int i = 0; i < n; i++)
	 	        messageVector[i][0] = (txt.charAt(i)) % 65;
	 	 
	 	    int cipherMatrix[][] = decryptBlock(messageVector,n);
	 	
	 	    for (int i = 0; i < n; i++){
	 	    	char ch = (char)(cipherMatrix[i][0] + 65);
	 	        CipherText += ch;
	 	    }
	    }

	    return CipherText;
	}
	
	public static String generateKey(int matDim){
		String key;
		Random rand = new Random();
		while(true){
			key = "";
		    for (int i = 0; i < matDim*matDim; i++) {
					key += (char)((rand.nextInt(26)%26)+65);
			}
			try{
				HillCipher hc = new HillCipher(key);
				String ck = key.substring(0,matDim);
				if(hc.decrypt(hc.encrypt(ck)).equals(ck)){
					break;
				}
			}
			catch(Exception exp){}
		}
		
		return key;
	}
}




