package assignment1;

public class Message {
	
	public String message;
	public int lengthOfMessage;

	public Message (String m){
		message = m;
		lengthOfMessage = m.length();
		this.makeValid();
	}
	
	public Message (String m, boolean b){
		message = m;
		lengthOfMessage = m.length();
	}
	
	/**
	 * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
	 */
	public void makeValid(){
		message = message.replaceAll("[^\\w]", "");
		message = message.replaceAll("\\d", "");
		message = message.toLowerCase();
		lengthOfMessage = message.length();
	}
	
	/**
	 * prints the string message
	 */
	public void print(){
		System.out.println(message);
	}
	
	/**
	 * tests if two Messages are equal
	 */
	public boolean equals(Message m){
		if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
			return true;
		}
		return false;
	}
	
	/**
	 * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
	 * @param key
	 */
	public void caesarCipher(int key){
		char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
				'o','p','q','r','s','t','u','v','w','x','y','z'};

		key = key % 26;
		if(key == 0)
			return;
		int temp =0;
		for(int i = 0 ; i<26 ; i++){
			if((i+key)<0)
				temp = i+key+26;
			else
				temp = i+key;
			message = message.replaceAll(alpha[i]+"", Character.toUpperCase(alpha[((temp)%26)])+"");
		}
		message = message.toLowerCase();
	}
	
	public void caesarDecipher(int key){
		this.caesarCipher(- key);
	}
	
	/**
	 * caesarAnalysis breaks the Caesar cipher
	 * you will implement the following algorithm :
	 * - compute how often each letter appear in the message
	 * - compute a shift (key) such that the letter that happens the most was originally an 'e'
	 * - decipher the message using the key you have just computed
	 */
	public void caesarAnalysis(){
		int[] occurence = new int[2];
		int temp = 0;
		occurence[0] = occurence[1] = 0;
		char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
				'o','p','q','r','s','t','u','v','w','x','y','z'};
		for(int i=0 ; i<26 ; i++){
			temp = message.length() - message.replaceAll(alpha[i] +"", "").length();
			if(temp > occurence[0]){
				occurence[0] = temp;
				occurence[1] = i;
			}
		}
		temp = occurence[1] - 4 ;
		caesarDecipher(temp);
	}
	
	/**
	 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
	 * @param key
	 */
	public void vigenereCipher (int[] key){
		char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		String result = "";
		int j = 0;
		int temp = 0;
		for(int i = 0 ; i < message.length() ; i++ ){
			for(j = 0 ; j < 26 ; j++){
				if(alpha[j] == message.charAt(i))
					break;
			}
			temp = (key[i%key.length] + j) % 26;
			if (temp < 0)
				temp = 26-temp;
			result += alpha[temp];
		}
		message = result;
	}

	/**
	 * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
	 * @param key
	 */
	public void vigenereDecipher (int[] key){
		for(int i = 0 ; i < key.length ; i++)
			key[i] = -key[i];
		vigenereCipher(key);
	}
	
	/**
	 * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
	 * @param key
	 */
	public void transpositionCipher (int key){
		int i ,j;
		if(key > message.length())
			return;
		int remain = message.length() % key ; 
		for(int g = 0 ; g<(key - remain) ; g++)
			message += "*";
		int temp = message.length() / key;
		String result = "";
		for(i = 0 ; i<key ; i++){
			for(j = 0 ; j<temp ; j++){
				result += message.charAt(i + j*key);
			}
		}
		message =result;
		lengthOfMessage = message.length();
	}
	
	/**
	 * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
	 * @param key
	 */
	public void transpositionDecipher (int key){
		String result = "";
		int step = message.length()/key;

		for(int j = 0 ; j<(step); j++)
			for(int i = 0 ; i<key;i++)
				result += message.charAt( j+i *step);
		message = result.replaceAll("\\*", "");
		lengthOfMessage = message.length();
	}
	
}
