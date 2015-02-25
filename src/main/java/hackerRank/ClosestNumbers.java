package hackerRank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ClosestNumbers {
	
	public static class Pair implements Comparable<Pair> {
		
		int x,y;

		@Override
		public int compareTo(Pair o) {
			int diff1 = Math.abs(x-y);
			int diff2 = Math.abs(o.x - o.y);			
			return diff1-diff2;
		}
		
		public int diff(){
			return x-y;
		}
		
		public String toString(){
			return "("+x+","+y+") ";
		}
	}
	
	public static void main(String[] args) {		
		Scanner scanner = new Scanner(System.in);		
		int N = Integer.valueOf(scanner.next());
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<N;i++){
			list.add(Integer.valueOf(scanner.next()));
		}
		Collections.sort(list);
		findClosestNumbers(list);
	}

	private static void findClosestNumbers(List<Integer> list) {		
		List<Pair> pairList = new ArrayList<Pair>();
		for(int i=0;i<list.size()-1;i++){
			Pair pair = new Pair();
			pair.x = list.get(i);
			pair.y = list.get(i+1);
			pairList.add(pair);
		}
		Collections.sort(pairList);
		//System.out.println("Sorted pair list " + pairList);
		Pair firstPair = pairList.get(0);
		for(int i=0;i<pairList.size();i++){
			Pair currentPair = pairList.get(i);
			if(pairList.get(i).diff() == firstPair.diff()){
				if(i>0)
					System.out.print(" ");
				System.out.print(pairList.get(i).x);
				System.out.print(" " + pairList.get(i).y);
			} else {
				break;				
			}
		}
	}

}

