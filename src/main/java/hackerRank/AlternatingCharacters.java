package hackerRank;

import java.util.Scanner;

public class AlternatingCharacters {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int T = Integer.valueOf(scanner.next());
		for(int i=0;i<T;i++){
			String str = scanner.next();
			printMinimumChars(str);
		}
		scanner.close();
	}

	private static void printMinimumChars(String str) {
		char current='A';
		char prev=' ';
		char[] buffer= str.toCharArray();
		int repeatitions=0;
		for(int i=0;i<buffer.length;i++){
			current = buffer[i];
			if(i>0 && current==prev){
				repeatitions++;
			}
			prev=current;
		}
		System.out.println(repeatitions);
	}

}
