package leetCode.longestSubStr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a string, find the length of the longest substring without repeating characters. 
 * For example, the longest substring without repeating letters for "abcabcbb" is "abc", 
 * which the length is 3. For "bbbbb" the longest substring is "b", with the length of 1.
 * 
 * @author tushark
 *
 */
public class Solution {
	
    public static int lengthOfLongestSubstring_UnOptimized(String s) {
    	boolean DEBUG=true;
    	int maxLength=0;
    	
    	int i=0;		
    	while(i<s.length()){
    		
    		Set<Character> viewedChars = new LinkedHashSet<Character>();
    		Map<Character,Integer> lastSeenAt = new HashMap<Character,Integer>();
    		
    		int lastJ=i;
    		if(DEBUG)
    			System.out.println("Iteration for index# " + i + " Start char="+ s.charAt(i) + " maxLen="+maxLength + "startJ="+lastJ);
			for (int j = i; j < s.length(); j++) {
				lastJ=j;
				char cr = s.charAt(j);
				if(j>2){
					char prevCr = s.charAt(j-1);
					if(prevCr==cr){
						i = j;
						if(DEBUG)
							System.out.println("\tJumping since RepeatCharFound at index="+j + " Length="+viewedChars.size());
						if(viewedChars.size()> maxLength)
							maxLength = viewedChars.size();
						break;
					}
				}
				
				if (!viewedChars.contains(cr)){
					viewedChars.add(cr);
					lastSeenAt.put(cr,j);
				}
				else{
					if(DEBUG)
						System.out.println("\tRepeatCharFoundcr="+ cr + " for index="+i+" at index="+j + " Length="+viewedChars.size() + " JumpTo=" + lastSeenAt.get(cr));
					i = lastSeenAt.get(cr);					
					if(viewedChars.size()> maxLength)
						maxLength = viewedChars.size();
					//i = j+1;
					//System.out.println("Restarting at index="+i);
					break;				
				}
			}
			if(DEBUG)
    			System.out.println("\tIteration end for index# " + i + " maxLen="+maxLength + " lastJ="+lastJ + " viewCharacters=" + printSet(viewedChars));
			i++;
		}
        return maxLength;
    }
    
    /*public static int lengthOfLongestSubstring(String s) {
    	
    }*/
    
    private static String printSet(Set<Character> viewedChars) {
		StringBuilder sb = new StringBuilder();
		Iterator<Character> iterator = viewedChars.iterator();
		while(iterator.hasNext()){
			sb.append(iterator.next());
		}
		return sb.toString();
	}

	public static void main(String[] args) {
    	//System.out.println("Length for " + lengthOfLongestSubstring_UnOptimized("abcabcbb"));
    	System.out.println("Length for " + lengthOfLongestSubstring_UnOptimized("wlrbbmqbhcdarzowkkyhiddqscdxrjmowfrxsjybldbefsarcbynecdyggxxpklorellnmpapqfwkhopkmco"));    	
	}
}