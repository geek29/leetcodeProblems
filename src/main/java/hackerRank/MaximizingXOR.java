package hackerRank;

import java.util.Scanner;

public class MaximizingXOR {
	
	public static String toBinary(int number) {
		StringBuilder sb = new StringBuilder();
		int f = 1;
		while (f <= number) {
			int radix = number & f;
			if (radix == f)
				sb.append("1");
			else
				sb.append("0");
			f = f << 1;
		}
		sb.reverse();
		return sb.toString();
	}
	
	static int maxXor(int l, int r) {		
		int n1=1;
		int acc = n1*2;
		while(acc<=l){
			acc = acc*2;
			n1++;
		}
		
		int n2=1;
		acc = n2*2;
		while(acc<=r){
			acc = acc*2;
			n2++;
		}
		int bits=0;
		if(n1==n2){
			String x1 = toBinary(l);
			String x2 = toBinary(r);
			while((x1.charAt(0)=='1' && x2.charAt(0)=='1') ||
					(x1.charAt(0)=='0' && x2.charAt(0)=='0')){
				x1 = x1.substring(1);
				x2 = x2.substring(1);
			}
			if(x1.charAt(0)=='1')
				bits =x1.length();
			else
				bits =x2.length();
		}else{
			String x1 = toBinary(l);
			String x2 = toBinary(r);
			bits = n1 > n2 ? n1 : n2;
		}
		
		n1=1;
		acc = 2;
		while(n1<bits){
			acc = acc*2;
			n1++;
		}				
		return acc-1;
    }

	/**
	 * T1 : failing now fixed
	 * 786
	   900 : 255
	   
	   T2 : failing now fixed 
	   304
	   313 : 15	   
	   very peculiar case it has first higher 5 bits same so it will need to be removed
	   X1=100110000 x2=100111001
	 * @param args
	 */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int res;
        int _l;
        _l = Integer.parseInt(in.nextLine());
        
        int _r;
        _r = Integer.parseInt(in.nextLine());
        
        res = maxXor(_l, _r);
        System.out.println(res);
        
    }

}
