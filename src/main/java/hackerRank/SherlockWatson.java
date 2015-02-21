package hackerRank;

import java.util.Scanner;

public class SherlockWatson {
	
	public static void main(String[] args) {
		String str = null;
		Scanner scanner = new Scanner(System.in);
		str = scanner.next();
		int N = Integer.valueOf(str);
		int K = Integer.valueOf(scanner.next());
		int Q = Integer.valueOf(scanner.next());
		int array[] = new int[N];
		int i=0;
		for(i=0;i<N;i++)
			array[i] = Integer.valueOf(scanner.next());
		
		for(i=0;i<K;i++){
			int shift = array[N-1];
			System.arraycopy(array, 0, array, 1, N-1);
			array[0] = shift;
			//printArray("After " + i + "th shift",array);
		}
		
		for(i=0;i<Q;i++){
			int idx = Integer.valueOf(scanner.next());
			System.out.println(array[idx]);
		}
		
	}
	
	public static void printArray(String label, int[] array){
			System.out.println(label);
			for(int c=0;c<array.length;c++)
				System.out.print(array[c]+" ");
			System.out.println();
	}

}
