package hackerRank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SherlockPairs {
	
	public static long fact(int n){
		long accumulator=1;
		for(int i=1;i<=n;i++){
			accumulator*=i; 
		}
		return accumulator;
	}
	
	private static long MAX_VALUE=9999900000L;	
	
	public static long combinations(int n, int k){		
		long numero=n;
		int r=k;
		int acc=1;
		while(r>0){
			acc *= numero;			
			numero--;r--;
		}
		long denom = fact(k);
		return acc/denom;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);		
		int T = Integer.valueOf(scanner.next());
		for(int i=0;i<T;i++){
			int N = Integer.valueOf(scanner.next());
			List<Integer> list = new ArrayList<Integer>();			
			for(int j=0;j<N;j++)
				list.add(Integer.valueOf(scanner.next()));
			Collections.sort(list);
			int index=0;
			long count=0;
			while(index<list.size()-1){
				int t1 = list.get(index);
				int k=index+1;
				int seq=1;
				for(;k<list.size();k++){
					int t2 = list.get(k);
					if(t2 > t1){
						k--;
						break;
					}else
						seq++;
				}				
				if(seq>1){
					//System.out.println("Seq="+seq + " for t1="+t1);
					count += combinations(seq, 2)*2;
				}
				index+=seq;
			}
			System.out.println(count);
		}
		
	}

}

