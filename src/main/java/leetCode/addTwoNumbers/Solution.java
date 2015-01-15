package leetCode.addTwoNumbers;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given two linked lists representing two non-negative numbers. 
 * The digits are stored in reverse order and each of their nodes contain 
 * a single digit. Add the two numbers and return it as a linked list.
 * 
 * 	Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 *  Output: 7 -> 0 -> 8
 * 
 * @author tushark
 *
 */
public class Solution {	
	public static class ListNode{
		int val;
		 ListNode next;
		 ListNode(int x) {
			 val = x;
		     next = null;
		 }		 
	}
	
	public static ListNode toList(int number){
		List<Integer> digitList = new ArrayList<Integer>();
		int num = number;
		ListNode root = null;
		ListNode prev = null;
		while(num>0){
			int digit = num%10;
			ListNode listNode = new ListNode(digit);
			if(prev == null){
				root = listNode;				
			} else {
				prev.next = listNode;
			}
			prev = listNode;
			digitList.add(digit);
			num = num/10;
		}
		//System.out.println("DigitList for Number="+number + " is " + digitList);
		return root;
	}
	
	public static String nodeString(ListNode n1){
		 StringBuilder sb = new StringBuilder();
		 ListNode node = n1;
		 while(node!=null){
			 sb.append(node.val).append("-> ");
			 node = node.next;
		 }
		 return sb.toString();
	}
	
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	boolean DEBUG = true;
    	ListNode left = l1;
    	ListNode right = l2;
    	ListNode sum = null;
    	ListNode prevSum = null;
    	
    	while(left!=null && right!=null){
    		int sumtotal = left.val + right.val;
    		ListNode node = new ListNode(sumtotal);
    		if(prevSum==null)
    			sum = node;
    		else
    			prevSum.next = node;
    		left = left.next;
    		right = right.next;
    		prevSum = node;
    	}
    	
    	
    	while(left!=null){    		
    		ListNode node = new ListNode(left.val);
    		if(prevSum==null)
    			sum = node;
    		else
    			prevSum.next = node;
    		left = left.next;
    		prevSum = node;
    	}
    	
    	
    	while(right!=null){    		
    		ListNode node = new ListNode(right.val);
    		if(prevSum==null)
    			sum = node;
    		else
    			prevSum.next = node;
    		right = right.next;
    		prevSum = node;
    	}
    	if(DEBUG)
    		System.out.println("Before CarryForward " + nodeString(sum));
    	
    	prevSum = sum;
    	int carryForward = -1;
    	int index=0;
        while(prevSum!=null){
        	if(carryForward!=-1)
        		prevSum.val += carryForward;
			if (prevSum.val >= 10) {
				carryForward = prevSum.val/10;
				prevSum.val -= carryForward*10; 				
			} else
				carryForward = 0;
			if(DEBUG)
				System.out.println("index#"+index+" CF="+carryForward);
			
			if(prevSum.next==null && carryForward!=0){
				ListNode node = new ListNode(carryForward);
				prevSum.next = node;
				prevSum = node.next;
				if(DEBUG)
					System.out.println("index#"+index+" EXTRACF="+carryForward);
        	}else {
        		prevSum = prevSum.next;
            	index++;
        	}
        }
    	
    	return sum;
    }
    
    public static void test(int x, int y, int expected){
    	ListNode l1 = toList(x);
		ListNode l2 = toList(y);
		System.out.println("List1 " + nodeString(l1));
		System.out.println("List2 " + nodeString(l2));
		ListNode sum = addTwoNumbers(l1, l2);
		System.out.println("Result " + nodeString(sum));
		System.out.println("Expect " + nodeString(toList(expected)));
    }
    
    public static void main(String[] args) {
		test(345,12345,12690);
		test(5,5,10);
		test(9,9,18);
	}
}
