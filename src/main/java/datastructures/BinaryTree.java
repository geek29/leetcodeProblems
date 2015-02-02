package datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Problems following :
 * 		adding Node 
 * 		Traversals : pre, in, post-order
 * 		Search
 * 		Find of TreeHeight
 * 		CreateMinimalBST from sorted array
 * 		Delete
 * 		Create List Of Node For Depth : DFS
 * 		InOrderSuccessor for given node
 * 		Printing Tree
 * 
 * @author tushark
 *
 */
@SuppressWarnings("rawtypes")
public class BinaryTree {
	
	TreeNode root;
	boolean keepParent=false;	
	
	public static class TreeNode{
		
		public TreeNode(Comparable data2) {
			this.data = data2;			
		}
		TreeNode right=null, left=null;
		Comparable data;
		public TreeNode parent;
	}
	
	public void add(Comparable data){
		
		TreeNode node = new TreeNode(data);		
		if(root==null){
			root = node;
		}else {
			boolean added = false;
			TreeNode currentNode = root;
			while(!added){
				@SuppressWarnings("unchecked")
				int comparison = data.compareTo(currentNode.data);
				if(comparison>0){
					if(currentNode.right!=null){						
						currentNode = currentNode.right;						
					}
					else {						
						currentNode.right = node;
						if(keepParent){
							node.parent = currentNode;							
						}
						break;
					}
				} else {
					if(currentNode.left!=null){						
						currentNode = currentNode.left;
					}
					else {												
						currentNode.left = node;
						if(keepParent){
							node.parent = currentNode;							
						}
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Works only on perfectly balanced tree, modified to work for un-balanced tree
	 * but still not perfect looking
	 */
	public void printTree(){
		TreeNode currentNode = root;
		List<int[]> list = new ArrayList<int[]>();
		//list.add(new int[]{0,0,0});
		buildCoordinates(currentNode, list,0,0);
		int maxWidth = 0;
		int maxHeight = 0;
		for(int[] a : list){
			//System.out.println("counter="+a[0] + " level="+ a[1] + " d="+ a[2] );
			if(a[0]> maxWidth)
				maxWidth = a[0];
			if(a[1]> maxHeight)
				maxHeight = a[1];
		}
		//System.out.println("MaxHeight="+ maxHeight + " maxWidth="+ maxWidth);
		for(int i=0;i<=maxHeight;i++){
			StringBuilder sb = new StringBuilder();
			for(int j=0;j<=maxWidth;j++){
				boolean added= false;
				for(int[] a: list){
					if(a[0]==j && a[1]==i){						
						sb.append(a[2]);
						added = true;
					}
				}
				if(!added) sb.append("  ");
			}
			System.out.println(sb);
		}
		
	}

	private int buildCoordinates(TreeNode currentNode, List<int[]> list, int counter, int level) {		
		if(currentNode!=null){			
			int counterNew = buildCoordinates(currentNode.left, list,counter,level+1);
			list.add(new int[]{counterNew+1, level, (Integer)currentNode.data});			
			return buildCoordinates(currentNode.right,list,counterNew+1,level+1);
		}
		else return counter+1;
	}
	
	public void inOrderTraversal(IteratorFunction function){
		TreeNode currentNode = root;
		travelInOrder(currentNode,function);
	}

	private void travelInOrder(TreeNode currentNode, IteratorFunction function) {		
		if(currentNode!=null){			
			travelInOrder(currentNode.left, function);
			function.handle(currentNode.data);
			travelInOrder(currentNode.right,function);
		}
		else return;
	}
	
	public void preOrderTraversal(IteratorFunction function){
		TreeNode currentNode = root;
		travelPreOrder(currentNode,function);
	}

	private void travelPreOrder(TreeNode currentNode, IteratorFunction function) {		
		if(currentNode!=null){
			function.handle(currentNode.data);
			travelInOrder(currentNode.left, function);			
			travelInOrder(currentNode.right,function);
		}
		else return;
	}
	
	public void postOrderTraversal(IteratorFunction function){
		TreeNode currentNode = root;
		travelPostOrder(currentNode,function);
	}

	private void travelPostOrder(TreeNode currentNode, IteratorFunction function) {		
		if(currentNode!=null){			
			travelInOrder(currentNode.left, function);			
			travelInOrder(currentNode.right,function);
			function.handle(currentNode.data);
		}
		else return;
	}
	
	@SuppressWarnings("unchecked")
	public boolean search(Comparable object){
		TreeNode currentNode = root;
		while(true){
			if(object.equals(currentNode.data))
				return true;
			else {
				int comparison = object.compareTo(currentNode.data);
				if(comparison>0){
					if(currentNode.right!=null)
						currentNode = currentNode.right;
					else return false;
				} else {
					if(currentNode.left!=null)
						currentNode = currentNode.left;
					else return false;
				}
			}
		}	
	}
	
	public final static int RIGHT=0;
	public final static int LEFT=1;
	public boolean delete(Comparable object){
		
		//TODO : Handle special case of deletion of Root;
		
		TreeNode currentNode = root;
		TreeNode parent = null;
		int childType = LEFT;
		while(true){
			if(object.equals(currentNode.data)){
				deleteNode(currentNode,parent, childType);
				return true;
			}
			else {
				int comparison = object.compareTo(currentNode.data);
				if(comparison>0){
					if(currentNode.right!=null){
						parent = currentNode;
						currentNode = currentNode.right;
						childType = RIGHT;
					}
					else return false;
				} else {
					if(currentNode.left!=null){
						parent = currentNode;
						currentNode = currentNode.left;
						childType = LEFT;
					}
					else return false;
				}
			}
		}		
	}
	
	private void deleteNode(TreeNode currentNode, TreeNode parent, int type) {
		
		if(currentNode.left==null && currentNode.right==null){
			System.out.println("Deleting "+ currentNode.data);
			switch(type){
				case RIGHT : parent.right = null;return;
				case LEFT : parent.left = null;return;
			}
		}
		
		TreeNode leftChild = currentNode.left;
		if(leftChild==null){
			switch(type){
				case RIGHT : parent.right = currentNode.right;return;
				case LEFT : parent.left = currentNode.right;return;
			}			
		}else{
			TreeNode prev = leftChild;
			TreeNode runningNode = leftChild.right;
			while(runningNode!=null){
				prev = runningNode;
				runningNode = runningNode.right;
			}
			prev.right = null;
			switch(type){
				case RIGHT : 
					prev.right = currentNode.right;
					parent.right = prev;
					break;
				case LEFT :
					prev.right = currentNode.left;
					parent.left = prev;
					break;
			}
		}
	}

	public int treeHeight(){
		TreeNode currentNode = root;
		int maxHeight = travelForTreeHeight(currentNode,0);
		return maxHeight;
	}
	
	private int travelForTreeHeight(TreeNode currentNode, int maxHeight) {
		if(currentNode==null)
			return maxHeight;
		int leftHeight = travelForTreeHeight(currentNode.left,maxHeight+1);
		int rightHeight = travelForTreeHeight(currentNode.right,maxHeight+1);
		return leftHeight > rightHeight ? leftHeight : rightHeight ;
	}

	/*
	 * Given a sorted (increasing order) array, write an algorithm to create a binary tree with
	   minimal height.
	   Need recursive solution**
	 */
	public void createMinimumHeightTree(Comparable[] sortedStringArray){		
		int n = sortedStringArray.length;
		int index=1;
		while(n>1){
			n=n/2;
			index++;
		}		
		System.out.println("Creating Tree of Height " + index);
		root = addToTree(sortedStringArray,0,sortedStringArray.length-1);
	}
	
	private TreeNode addToTree(Object arr[], int start, int end) {
		if (end < start) {
			return null;
		}
		int mid = (start + end) / 2;
		TreeNode n = new TreeNode((Comparable)arr[mid]);
		if (!keepParent) {
			n.left = addToTree(arr, start, mid - 1);
			n.right = addToTree(arr, mid + 1, end);
		} else {
			TreeNode left = addToTree(arr, start, mid - 1);
			n.left = left;
			if(left!=null)
				left.parent = n;
			TreeNode right = addToTree(arr, mid + 1, end);
			n.right = right;
			if(right!=null)
				right.parent = n;
		}
		return n;
	}
	
	public void createListOfNodeForDepthD(){
		Map<Integer,LinkedList> map = new HashMap<Integer,LinkedList>();
		TreeNode currentNode = root;
		buildListForDepth(currentNode,map,0);
		
		for(int i=0;i<map.size();i++){
			LinkedList listGen = map.get(i);
			final StringBuilder sb = new StringBuilder();
			listGen.iterate(new IteratorFunction(){
				public void handle(Object object){
					sb.append(object).append("->");
				}
			});
			System.out.println("List " + i + " :" + sb);
		}		
		
	}

	private void buildListForDepth(TreeNode currentNode, Map<Integer,LinkedList> map ,int depth) { 		
		if(currentNode!=null){			
			buildListForDepth(currentNode.left,map, depth+1);
			LinkedList linkedList=map.get(depth);						
			if(linkedList==null){
				linkedList = new LinkedList();
				map.put(depth,linkedList);
			}
			linkedList.add(currentNode.data);
			buildListForDepth(currentNode.right,map,depth+1);
		}
		else return;
	}
	
	
	public Object inOrderSuccessor(Comparable object) {
		TreeNode currentNode = root;
		TreeNode parent = null;
		while (true) {
			if (object.equals(currentNode.data)) {

				if (parent.right == currentNode) {
					// its right child so its parent's parent
					if (currentNode.left == null && currentNode.right == null) {
						return currentNode.parent.parent.data;
					} else if (currentNode.right != null) {
						// first right and then extrem left
						TreeNode runner = currentNode.right;
						TreeNode prev = currentNode.right;
						while (runner != null) {
							prev = runner;
							runner = runner.left;
						}
						return prev.data;
					}
				} else {
					if (currentNode.right == null)
						return currentNode.parent.data;
					else {
						// first right and then extreme left
						TreeNode runner = currentNode.right;
						TreeNode prev = currentNode.right;
						while (runner != null) {
							prev = runner;
							runner = runner.left;
						}
						return prev.data;
					}
				}
			} else {
				int comparison = object.compareTo(currentNode.data);
				if (comparison > 0) {
					if (currentNode.right != null) {
						parent = currentNode;
						currentNode = currentNode.right;
					} else
						return null;
				} else {
					if (currentNode.left != null) {
						parent = currentNode;
						currentNode = currentNode.left;
					} else
						return null;
				}
			}
		}
	}
	
	public static void testTraversals(){
		BinaryTree tree = new BinaryTree();
		tree.add("c");
		tree.add("a");		
		tree.add("q");
		tree.add("l");
		tree.add("z");
		System.out.println("Tree Height = " + tree.treeHeight());
		
		System.out.println("InOrder Traversal : ");
		tree.inOrderTraversal(new IteratorFunction(){
			@Override
			public void handle(Object iterObject) {
				System.out.println(iterObject);				
			}			
		});
		
		System.out.println("PreOrder Traversal : ");
		tree.preOrderTraversal(new IteratorFunction(){
			@Override
			public void handle(Object iterObject) {
				System.out.println(iterObject);				
			}			
		});
		
		System.out.println("PostOrder Traversal : ");
		tree.postOrderTraversal(new IteratorFunction(){
			@Override
			public void handle(Object iterObject) {
				System.out.println(iterObject);				
			}			
		});
	}
	
	public static void testSearch(){
		BinaryTree tree = new BinaryTree();
		tree.add("c");
		tree.add("a");		
		tree.add("q");
		tree.add("l");
		tree.add("z");
		System.out.println("Tree Height = " + tree.treeHeight());
		
		System.out.println("Search a : " + tree.search("a"));
		System.out.println("Search b : " + tree.search("b"));
		System.out.println("Search a : " + tree.search("q"));
	}
	
	public static void testTreeHeight(){
		
		BinaryTree tree2 = new BinaryTree();
		tree2.add(4);
		tree2.add(2);
		tree2.add(1);
		tree2.add(3);
		tree2.add(6);
		tree2.add(5);
		tree2.add(7);
		System.out.println("Balanced Tree2 Height=" + tree2.treeHeight());
		
		
		BinaryTree tree3 = new BinaryTree();
		tree3.add(1);
		tree3.add(2);
		tree3.add(3);
		tree3.add(4);
		tree3.add(5);
		tree3.add(6);
		tree3.add(7);
		System.out.println("Balanced Tree3 Height=" + tree3.treeHeight());
		
	}
	
	public static void testCreateMinimalBST(){
		BinaryTree tree4 = new BinaryTree();
		Comparable[] a = new Comparable[]{1, 2, 3, 4, 5, 6, 7};
		tree4.createMinimumHeightTree(a);
		System.out.println("InOrder Traversal : ");
		tree4.inOrderTraversal(new IteratorFunction(){
			@Override
			public void handle(Object iterObject) {
				System.out.println(iterObject);				
			}			
		});
		
		System.out.println("PrintTree");
		tree4.printTree();
		
		System.out.println("------------------------------------------------------------");
	}
	
	public static void testDelete(){
		BinaryTree tree4 = new BinaryTree();
		Comparable[] a = new Comparable[]{1, 2, 3, 4, 5, 6, 7};
		tree4.createMinimumHeightTree(a);
		tree4.printTree();
		System.out.println("------------------------------------------------------------");
		

		System.out.println("Delete 6 : " + tree4.delete(6)); 
		System.out.println("PrintTree");
		tree4.printTree();
		
		
		System.out.println("------------------------------------------------------------");
		
		
		
		System.out.println("Delete 3 : " + tree4.delete(3));
		System.out.println("PrintTree");
		tree4.printTree();
		
		tree4.inOrderTraversal(new IteratorFunction(){
			@Override
			public void handle(Object iterObject) {
				System.out.println(iterObject);				
			}			
		});
		
		System.out.println("------------------------------------------------------------");
		
		//tree4.add(3);
		tree4.add(1);
		
		tree4.inOrderTraversal(new IteratorFunction(){
			@Override
			public void handle(Object iterObject) {
				System.out.println(iterObject);				
			}			
		});
		
		System.out.println("PrintTree afrer adding 3 and 1");
		tree4.printTree();
		
		System.out.println("------------------------------------------------------------");
		
		System.out.println("Delete 6 : " + tree4.delete(6)); 
		System.out.println("PrintTree");
		tree4.printTree();
		
		System.out.println("------------------------------------------------------------");
		
		System.out.println("Delete 7 : " + tree4.delete(7)); 
		System.out.println("PrintTree");
		tree4.printTree();
		
		System.out.println("------------------------------------------------------------");
		tree4.add(0);
		System.out.println("Add 0 "); 
		System.out.println("PrintTree");
		tree4.printTree();		

	}
	
	public static void testCreateListOfNodeForDepthD(){
		BinaryTree tree4 = new BinaryTree();
		Comparable[] a = new Comparable[]{1, 2, 3, 4, 5, 6, 7};
		tree4.createMinimumHeightTree(a);
		tree4.printTree();
		System.out.println("------------------------------------------------------------");
		tree4.createListOfNodeForDepthD();
	}
	
	public static void testInOrderSuccessor(){
		System.out.println("------------------------------------------------------------");
		BinaryTree tree4 = new BinaryTree();
		tree4.keepParent = true;
		Comparable[] a = new Comparable[]{1, 2, 3, 4, 5, 6, 7};
		tree4.createMinimumHeightTree(a);
		tree4.printTree();
		System.out.println("Successor for 1 : " + tree4.inOrderSuccessor(1));
		System.out.println("Successor for 2 : " + tree4.inOrderSuccessor(2));
		System.out.println("Successor for 3 : " + tree4.inOrderSuccessor(3));
		System.out.println("Successor for 5 : " + tree4.inOrderSuccessor(5));
		System.out.println("Successor for 7 : " + tree4.inOrderSuccessor(7));
		System.out.println("------------------------------------------------------------");
	}
		
	public static void main(String[] args) {
		testTraversals();
		testSearch();
		testTreeHeight();
		testCreateMinimalBST();
		testDelete();
		testCreateListOfNodeForDepthD();
		testInOrderSuccessor();
				
	}

}

