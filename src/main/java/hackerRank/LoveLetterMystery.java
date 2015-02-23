package hackerRank;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/the-love-letter-mystery
 * @author tushark
 *
 */
public class LoveLetterMystery {
	
	public static void numChanges(String str){
		int length = str.length();
		int numChanges=0;
		for (int i = 0; i < length / 2; i++) {
			int k = length-i-1;
			char c1 = str.charAt(i);
			char c2 = str.charAt(k);
			if(c1!=c2){
				if(c1>c2){
					numChanges += c1 - c2;
				}else{
					numChanges += c2 - c1;
				}
			}
		}
		System.out.println(numChanges);
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int T = Integer.valueOf(scanner.next());
		for(int i=0;i<T;i++){
			String str = scanner.next();
			numChanges(str);
		}
		scanner.close();
	}

}
