package datastructures;



/**
 * 
 * LinkedList
 * 		add/delete
 * 		lastNthNode
 * 		reverse
 * 		deleteDuplicates
 * 		detechLoopInList
 * 
 * @author tushark
 *
 */
public class LinkedList {
	
	public static class Node {
		
		public Node(Object nodeData) {
			this.data = nodeData;
		}
		
		public Node next;
		public Object data;
	}
	
	private Node rootNode;
	
	public LinkedList(){		
	}
	
	public void add(Object nodeData){
		Node node = new Node(nodeData);
		if(rootNode!=null) {			
			Node lastNode = lastNode();
			lastNode.next = node;
		} else {
			rootNode = node;
		}
	}
	
	public void delete(Object nodeData){
		Node node = rootNode;
		Node prev = null;
		while(node!=null){
			if(nodeData.equals(node.data)){
				if(prev==null)
					rootNode = rootNode.next;
				else
					prev.next = node.next;
				break;
			}			
			prev = node;
			node = node.next;
		}		
	}

	private Node lastNode() {
		if(rootNode==null)
			return rootNode;
		Node node = rootNode;
		while(node.next!=null)
			node = node.next;
		return node;
	}
	
	public void iterate(IteratorFunction function){
		if(rootNode==null)
			return;		
		Node node = rootNode;
		while(node.next!=null){
			function.handle(node.data);
			node = node.next;
		}
		function.handle(node.data);
	}
	
	public void reverse(){
		Node startNode = rootNode;
		Node workingNode = startNode.next;		
		Node prevNode = null;
		while(workingNode!=null){			
			Node temp = workingNode.next;
			workingNode.next = startNode;			
			startNode.next = prevNode;
			prevNode = startNode;
			startNode = workingNode;			
			workingNode = temp;
			if(startNode!=null)
				rootNode=startNode;
		}
	}
	
	/*Write code to remove duplicates from an unsorted linked list.*/
	public void deleteDuplicates(){
		LinkedList set = new LinkedList();
	}
	
	/*How would you solve this problem if a temporary buffer is not allowed?
	 * Use two pointers one current second running from start to current
	 * 	If runner data matches with current delete current and the move on
	 * */
	public void deleteDuplicates2(){
		LinkedList set = new LinkedList();
	}
	
	/*Implement an algorithm to find the nth to last element of a singly linked list
	 * 
	 * Two pointers with distance between them = n. Move till second pointer reaches end
	 * 
	 * 	  P1        P2
	 * 	       P1        P2
	 *              P1        P2
	 *                   P2        P2
	 *    a -> b -> c -> d -> f -> g
	 * 
	 * 2nd Lst Element pointed by p2
	 * 
	 * */
	public Object nthLastElement(int n){
		Node n1 = rootNode;
		Node n2 = rootNode;
		for(int i=0;i<n-1;i++)
			if(n2.next!=null)
				n2 = n2.next;
		if(n2==null)
			return null;
		while(n2.next!=null){
			n1 = n1.next;
			n2 = n2.next;			
		}			
		return n1.data;
	}
	
	
	/*
	 * Given a circular linked list, implement an algorithm which returns node at the begin-
		ning of the loop.
		DEFINITION
		Circular linked list: A (corrupt) linked list in which a node’s next pointer points to an
		earlier node, so as to make a loop in the linked list.
		EXAMPLE
		Input: A -> B -> C -> D -> E -> C [the same C as earlier]
		Output: C
		
		Solution  : First find out loop start in the list
		            Secondly have two pointer spaced at distance from start of linked list to the start of loop
		            Increment for K times.		
	 * */
	
	/*public Object detectLoopHeap(){
		Node n1 = rootNode;
		Node n2 = rootNode.next;
		while(n1.next!=null){
			n2 = n1.next.next;
			n1 = n1.next;			
			if(n2!=null && n1.data==n2.data){
				System.out.println("Voila loop found");
				return n1.data;
			}
		}
		return null;
	}*/
	
	
	public static void main(String[] args) {		
		LinkedList list = new LinkedList();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("c");
		System.out.println("LastNode -> " + list.lastNode().data);
		list.iterate(new IteratorFunction(){
			public void handle(Object data){
				System.out.println("-> " + data);
			}
		});
		
		System.out.println("3rdLast ->" + list.nthLastElement(3));
		System.out.println("5thLast ->" + list.nthLastElement(5));
		System.out.println("0thLast ->" + list.nthLastElement(0));
		//System.out.println("Any loop? " + list.detectLoopHeap());
		
		/*list.reverse();
		System.out.println("LastNode -> " + list.lastNode().data);
		list.iterate(new IteratorFunction(){
			public void handle(Object data){
				System.out.println("-> " + data);
			}
		});*/
		
		list.delete("b");
		System.out.println("After Delete middle b");
		list.iterate(new IteratorFunction(){
			public void handle(Object data){
				System.out.println("-> " + data);
			}
		});
		
		
		list.delete("a");
		System.out.println("After Delete starta ");
		list.iterate(new IteratorFunction(){
			public void handle(Object data){
				System.out.println("-> " + data);
			}
		});
		
		list.delete("e");
		System.out.println("After Delete end e");
		list.iterate(new IteratorFunction(){
			public void handle(Object data){
				System.out.println("-> " + data);
			}
		});
		
		
		
	}
	

}
