package hackerRank;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameOfThrones {
	
	public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        String inputString = myScan.nextLine();       
        String ans = isPalindrome(inputString) ? "YES" : "NO";        
        System.out.println(ans);
        myScan.close();
    }

	private static boolean isPalindrome(String inputString) {
		Map<Character,Integer> map = new HashMap<Character,Integer>();
		char[] array = inputString.toCharArray();
		for(Character c : array){
			if(map.containsKey(c)){
				int count = map.get(c);
				count++;
				map.put(c,count);
			} else{
				map.put(c,1);
			}
		}
		if(array.length%2==0){
			boolean isEven = true;
			for(int val : map.values()){
				if(val%2!=0){
					isEven = false;
					break;
				}
			}
			return isEven;
		}else{
			int odds=0;
			int evens=0;
			for(int val : map.values()){
				if(val%2!=0){
					odds++;
				}else
					evens++;
			}
			//System.out.println("odds="+odds + " evens="+ evens);
			if(odds!=1)
				return false;
			else return true;
		}
	}

}
