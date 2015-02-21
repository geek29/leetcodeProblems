package datastructures;

/**
 * 
 * Simple linkedList based queue
 * @author tushark
 *
 */
public class PriorityQueue {
	
	public static class Node {
		public Comparable data;
		public Node next;
		public Node prev;
	}
	
	private Node tail;
	private Node head;
	
	public void add(Comparable data){
		Node node = new Node();
		node.data = data;			
		if(tail==null){
			tail = node;
			head = tail;
			return;
		}else{
			Node runner = tail;
			Node prev = null;
			while(runner!=null){
				int diff = runner.data.compareTo(data);
				if(diff<=0){
					prev = runner;
					runner = runner.next;						
				}else{
					if(prev==null){
						node.next = tail;
						tail.prev = node;
						tail = node;							
					}else{
						prev.next = node;
						node.prev = prev;
						
						node.next = runner;
						runner.prev = node;
					}
					break;
				}
			}
			//reached at the end
			if(runner==null){
				prev.next = node;
				node.prev = prev;
				head = node;
			}
		}
	}
	
	public void print(){
		Node runner = tail;
		while(runner!=null){
			System.out.println(" "+ runner.data);
			runner = runner.next;						
		}
	}
	
	public Comparable take(){
		Node node = tail;
		tail = tail.next;
		node.next = null;
		return node.data;
	}
	
	public Comparable takeBack(){
		return take();
	}
	
	public Comparable takeFront(){
		Node node = head;
		head = head.prev;
		head.next = null;
		node.prev = null;
		return node.data;
	}
	
	public Comparable peek(){
		return head.data;
	}
	
	public static void testMe(){
		PriorityQueue queue = new PriorityQueue();
		queue.add(5);
		queue.add(4);
		queue.add(7);
		queue.add(9);
		queue.add(-1);
		queue.add(1);
		queue.print();
		System.out.println("Take " + queue.take());		
		queue.print();
		System.out.println("Peek " + queue.peek());
		System.out.println("Take " + queue.take());
		queue.print();
		System.out.println("Peek " + queue.peek());
		System.out.println("Take " + queue.take());
		queue.print();
		System.out.println("Peek " + queue.peek());
		System.out.println("TakeFront " + queue.takeFront());		
		System.out.println("Peek " + queue.peek());
		queue.print();
	}
	
	public static void main(String[] args) {
		testMe();
	}	

}
