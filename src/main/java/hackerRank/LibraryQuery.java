package hackerRank;

import java.util.Scanner;


/**
 * Solution to hackerRank problem : https://www.hackerrank.com/challenges/library-query
 * Soltion passes simple testcases but exceeds time for long test cases
 * Currently it takes around 14 second require timelimit is 4sec
 * @author tushark
 *
 */
public class LibraryQuery {
	
	public final static int UPDATE_BOOKS=1;
	public final static int QUERY_BOOKS=0;
	public static boolean DEBUG=true;
	
	public static void main(String[] args) {		
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		int T = Integer.valueOf(line);
		
		int numShelves = 0;
		int[] shelves=null;
		
		String str=null;
		for(int i=0;i<T;i++){
			str = scanner.next();
			numShelves = Integer.valueOf(str);
			shelves = new int[numShelves];
			for(int j=0;j<numShelves;j++){
				str = scanner.next();
				shelves[j] = Integer.valueOf(str);
			}
			str = scanner.next();
			int numQueries = Integer.valueOf(str);
			for(int k=0;k<numQueries;k++){
				str = scanner.next();
				int type = Integer.valueOf(str);				
				switch(type){
					case UPDATE_BOOKS :
						updateBooks(shelves,scanner);
						break;
					case QUERY_BOOKS :
						queryBooks(shelves,scanner);
						break;
				}
			}
		}	
	}
	
	
	public static void quickSort(int[] c, boolean descending){
		_quickSort(c, 0, c.length-1, descending);
	}
	
	@SuppressWarnings("rawtypes")
	public static void _quickSort(int[] c, int start, int end, boolean descending){
		if(end > start){
			int p = partition(c,start,end, descending);
			_quickSort(c,start,p-1, descending);
			_quickSort(c,p+1,end, descending);
		}
	}
	
	private static void swap(int[] c, int i, int pivot) {
		if(i!=pivot){
			int temp = c[i];
			c[i] = c[pivot];
			c[pivot] = temp;			
		}
	}

		
	@SuppressWarnings("unchecked")
	private static int partition(int[] c, int start, int end, boolean descending) {		
		int pivotIndex = start + (end-start)/2;
		int pivotValue = c[pivotIndex];		
		swap(c,end,pivotIndex);
		int partitionIndex=start;
		for(int i=start;i<end;i++){
			if(!descending){
				if(c[i] <= pivotValue){
					swap(c,i,partitionIndex);				
					partitionIndex++;
				}
			}else{
				if(c[i] > pivotValue){
					swap(c,i,partitionIndex);				
					partitionIndex++;
				}
			}
			
		}
		swap(c,partitionIndex, end);
		return partitionIndex;
	}

	private static void queryBooks(int[] shelves, Scanner scanner) {
		String str = scanner.next();
		int x = Integer.valueOf(str) - 1;
		str = scanner.next();
		int y = Integer.valueOf(str) - 1;
		str = scanner.next();
		int k = Integer.valueOf(str);
		int size = y-x+1;
		if (k > size / 2) {
			//that means we want to maintain sorted list from k to y descending
			//and return last element			
			int array[] = new int[size - k + 1];
			System.arraycopy(shelves, x, array, 0, (size - k + 1));
			quickSort(array, true);
			for(int i=(x+array.length);i<=y;i++){
				if(shelves[i] > array[array.length-1]){
					int c=0;
					while(array[c] > shelves[i])
						c++;
					System.arraycopy(array, c, array, c+1,(array.length-c-1));
					array[c] = shelves[i];			
				}				
			}
			System.out.println(array[array.length-1]);
		} else {
			//that means we want to maintain sorted list from 0 to k ascending
			//and return last element			
			int array[] = new int[k];
			System.arraycopy(shelves, x, array, 0, k);
			quickSort(array, false);
			for(int i=(x+array.length);i<=y;i++){
				if(shelves[i] < array[array.length-1]){
					int c=0;
					while(array[c] < shelves[i])
						c++;
					System.arraycopy(array, c, array, c+1,(array.length-c-1));
					array[c] = shelves[i];			
				}		
				
			}
			System.out.println(array[array.length-1]);		
		}
	}

	private static void updateBooks(int[] shelves, Scanner scanner) {
		String str = scanner.next();
		int x = Integer.valueOf(str) - 1;
		str = scanner.next();
		int k = Integer.valueOf(str);
		shelves[x] = k;
	}
	
	
	public static void printArray(String label, int[] array){
		if(DEBUG){
			System.out.println(label);
			for(int c=0;c<array.length;c++)
				System.out.println(array[c]);
		}		
	}	

}

