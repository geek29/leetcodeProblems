package datastructures;

import java.util.ArrayList;
import java.util.List;

public class Trie {
	
	//TODO : bit-wise tries
	public static class TrieNode {

		public char data;
		public List<TrieNode> children = null;
		
		public TrieNode(char charAt) {
			this.data = charAt;
			this.children = new ArrayList<TrieNode>();
		}

		public TrieNode() {
			
		}

	}

	private TrieNode rootNode;

	public void addKey(String key) {
		TrieNode currentNode = rootNode;
		if (currentNode == null) {
			rootNode = new TrieNode();
			rootNode.children = new ArrayList<TrieNode>();
			for (int i = 0; i < key.length(); i++) {
				rootNode.children.add(new TrieNode(key.charAt(i)));
			}
			return;
		}

		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			TrieNode t = findNext(currentNode.children, c);
			if (t != null) {
				currentNode = t;
			} else {
				t = new TrieNode(c);
				currentNode.children.add(t);
				currentNode = t;
			}
		}
	}

	public boolean exists(String key) {
		TrieNode currentNode = rootNode;
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			TrieNode t = findNext(currentNode.children, c);
			if (t == null)
				return false;
			else
				currentNode = t;
		}
		return true;
	}

	private TrieNode findNext(List<TrieNode> children, char c) {
		for (TrieNode t : children) {
			if (t.data == c)
				return t;
		}
		return null;
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.addKey("A");
		trie.addKey("to");
		trie.addKey("tea");
		trie.addKey("ted");
		trie.addKey("ten");
		trie.addKey("i");
		trie.addKey("in");
		trie.addKey("inn");

		System.out.println("A Exists? = " + trie.exists("A"));
		System.out.println("ten Exists? = " + trie.exists("ten"));
		System.out.println("ted Exists? = " + trie.exists("ted"));
		System.out.println("tede Exists? = " + trie.exists("tede"));
		System.out.println("i Exists? = " + trie.exists("i"));
		System.out.println("in Exists? = " + trie.exists("in"));
		System.out.println("inn Exists? = " + trie.exists("inn"));
		System.out.println("inb Exists? = " + trie.exists("inb"));
		trie.addKey("tede");
		System.out.println("Add tede; tede Exists? = " + trie.exists("tede"));
	}

}
