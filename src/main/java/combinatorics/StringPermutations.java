package combinatorics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 1. All permutations of given string
 * 2. All permutations of given string with specified group preserved intact
 * @author tushark
 *
 */
public class StringPermutations {
	
	public static void main(String[] args) {
		String str = "abcd";
		System.out.println("All permutations of " + str + " = " + print(perms(str)));
		System.out.println("All permutations (with group preserve) of " + str + " = " + print(permsPreserveGroup(str, "bc")));
		
		//test method for strings upto length of 10
		String testData = "abcdefghijkp";
		for(int i=2;i<10;i++){
			String testCase = testData.substring(0,i);
			Set<String> perms = getSet(perms(testCase)); //make sure all generated strings are unique
			int expectedSize = factorial(i); // n! permutations possible for string of length n
			System.out.println("For i="+testCase.length() + " numPers="+ perms.size() + " expectedSize="+expectedSize + 
					(perms.size()== expectedSize ? " PASS" : " FAIL"));
		}
	}
	
	private static Set<String> getSet(List<String> perms) {
		Set<String> set = new HashSet<String>();
		for(String s : perms){
			set.add(s);
		}
		return set;
	}

	public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

	private static String print(List<String> permutation) {
		StringBuilder sb = new StringBuilder();
		for(String s : permutation){
			sb.append(s).append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * for string of length 1 : perms(str)  = str
	 * for string of length 2 : perms(str)  = [ab, ba]
	 * for string of length 3 : perms(str)  = a|bc = a+perms(bc), perms(bc)+a, put a in middle of perms[bc]
	 * Thus generalize above algo 
	 */
	static List<String> perms(String s){
		if(s.length()==1){
			List<String> strList = new ArrayList<String>();
			strList.add(s);
			return strList;
		}
		else if(s.length()==2){
			List<String> strList = new ArrayList<String>();
			strList.add(s);
			strList.add(new String(new char[]{s.charAt(1),s.charAt(0)}));
			return strList;
		}
		else {
			char a = s.charAt(0);
			List<String> perms = perms(s.substring(1));
			List<String> strList = new ArrayList<String>();
			
			// p(bc) + a
			for(String perm : perms){
				StringBuilder sb = new StringBuilder(perm);
				strList.add(sb.append(a).toString());
			}
			// a in middle of p(bc)			
			for(String perm : perms){				
				for(int k=1;k<perm.length();k++){
					StringBuilder sb = new StringBuilder();				
					int i=0;
					for(i=0;i<k;i++)
					sb.append(perm.charAt(i));
					sb.append(a);
					for(;i<perm.length();i++)
						sb.append(perm.charAt(i));
					strList.add(sb.toString());	
				}			
			}
			
			//a+ p(bc)
			for(String perm : perms){
				StringBuilder sb = new StringBuilder();
				sb.append(a);
				sb.append(perm);
				strList.add(sb.toString());
			}
			
			return strList;
		}
	}
	
	/**
	 * When group is specified instead of selecting anchor element a first character
	 * select the entire group if passed string starts with the group. This will
	 * ensure all characters in group remain together in orginal order 
	 * @param s
	 * @param group
	 * @return
	 */
	static List<String> permsPreserveGroup(String s, String group){
		if(s.length()==1){
			List<String> strList = new ArrayList<String>();
			strList.add(s);
			return strList;
		}
		else if(s.length()==2){
			List<String> strList = new ArrayList<String>();
			strList.add(s);
			strList.add(new String(new char[]{s.charAt(1),s.charAt(0)}));
			return strList;
		} else if(s.length()==group.length() && s.equals(group)){
			List<String> strList = new ArrayList<String>();
			strList.add(s);
			return strList;
		}
		else {			
			String a = s.substring(0, 1);
			if(s.startsWith(group))
				a = group;
			List<String> perms = permsPreserveGroup(s.substring(a.length()),group);
			List<String> strList = new ArrayList<String>();
			
			// p(bc) + a
			for(String perm : perms){
				StringBuilder sb = new StringBuilder(perm);
				strList.add(sb.append(a).toString());
			}
			
			// a in middle of p(bc)			
			for(String perm : perms){				
				for(int k=1;k<perm.length();k++){
					StringBuilder sb = new StringBuilder();				
					int i=0;
					for(i=0;i<k;i++)
					sb.append(perm.charAt(i));
					sb.append(a);
					for(;i<perm.length();i++)
						sb.append(perm.charAt(i));
					strList.add(sb.toString());	
				}
				
			}
			
			//a+ p(bc)
			for(String perm : perms){
				StringBuilder sb = new StringBuilder();
				sb.append(a);
				sb.append(perm);				
				strList.add(sb.toString());
			}
			
			return strList;
		}
	}

}
