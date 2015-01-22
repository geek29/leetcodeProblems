package leetCode.lruCache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class LRUCache {
	
	private static final boolean DEBUG=false;
	
	public static class Usage  implements Comparable<Usage>{
		public long timestamp;
		public int key;
		
		public Usage(int key){
			this.key = key;
			this.timestamp = System.nanoTime();
		}
		
		public Usage(int key, long ts){
			this.key = key;
			this.timestamp = ts;
		}

		@Override
		public int compareTo(Usage o) {			
				return (int)(this.timestamp - o.timestamp);			
		}
		
		public String toString(){ 
			return "U(k="+key +",ts="+timestamp+")";
		}
	}
	
	public static class ValueBag {
		public ValueBag(int key2, int value2) {
			this.key = key2;
			this.value = value2;
			this.lastTs = System.nanoTime();
		}
		
		public int key;
		public int value;
		public long lastTs;
		
		public String toString(){
			return "VB(k="+key+" ts="+lastTs+" val="+value+") ";
		}
	}

	private static final int GET = 0;
	private static final int SET = 1;
	
	private Map<Integer,ValueBag> backingMap = null;
	private SortedSet<Usage> keyUsage = null;
	private int capacity = 0;
    
    public LRUCache(int capacity) {
        backingMap = new HashMap<Integer,ValueBag>(capacity);
        this.capacity = capacity;
        keyUsage = new TreeSet<Usage>();                              
    }
    
    private void used(int key , long lastTs, int op, ValueBag vb){
    	if(DEBUG)
    		printUsageMap("Before ", keyUsage);
    	
    	Usage usage = new Usage(key,lastTs);
    	SortedSet map = keyUsage.tailSet(usage);
    	boolean found = false;
    	Usage reference = null;
    	Iterator<Usage> iterator = map.iterator();
    	while(iterator.hasNext()){
    		Usage storedUsage = iterator.next();
    		if(DEBUG)
    			System.out.println("Curent Key="+ storedUsage.key +" ts="+ storedUsage.timestamp + " skey="+key + " sts="+lastTs);
    		if(storedUsage.timestamp > lastTs)
    			break;
    		else if(storedUsage.timestamp==lastTs && storedUsage.key==key){
    			reference = storedUsage;
    			found = true;
    		}
    	}
    	
    	if(found){
    		boolean removed = keyUsage.remove(reference);    		
    		reference.timestamp = System.nanoTime();
    		vb.lastTs = reference.timestamp;
    		keyUsage.add(reference);
    		if(DEBUG)
    			System.out.println("Usage ts for key="+key + " is now="+reference.timestamp  + " for op=" + opCdoe(op) 
    				+ " UPDATE oldTs="+ lastTs + " hasRemoved=" + removed + " size=" + keyUsage.size());
    	} else {
    		keyUsage.add(usage);
    		if(DEBUG)
    			System.out.println("Usage ts for key="+key + " is now="+usage.timestamp  + " for op=" + opCdoe(op) + " INSERT");
    	}
    	
    	if(DEBUG)
    		printUsageMap("After ", keyUsage);
    }
    
	private void printUsageMap(String tag, SortedSet<Usage> keyUsage2) {
		StringBuilder sb = new StringBuilder(tag);
		sb.append("UsageMap(size=");
		sb.append(keyUsage2.size()).append(") : ");
		Iterator<Usage> iterator = keyUsage2.iterator();
		while(iterator.hasNext()){
			sb.append(iterator.next()).append(" ");
		}
		System.out.println(sb);
	}

	private String opCdoe(int op) {
		if(op==GET)
			return "GET";
		else if(op==SET)
			return "SET";
		return null;
	}

	private int evict() {
		Usage usage = keyUsage.first();
		if(DEBUG)
			System.out.println("Evicted " + usage.key + " with ts="+usage.timestamp);
		return usage.key;
	}
	
	private void gc(ValueBag bag) {
		Usage usage = new Usage(bag.key,bag.lastTs);
    	SortedSet map = keyUsage.tailSet(usage);
    	boolean found = false;
    	Usage reference = null;
    	Iterator<Usage> iterator = map.iterator();
    	while(iterator.hasNext()){
    		Usage storedUsage = iterator.next();
    		if(storedUsage.timestamp> bag.lastTs)
    			break;
    		else if(storedUsage.timestamp==bag.lastTs && storedUsage.key==bag.key){
    			reference = storedUsage;
    			found = true;
    		}
    	}
    	
    	if(found){
    		boolean removed = keyUsage.remove(reference);
    		if(DEBUG)
    			System.out.println("GC for key="+bag.key + " for ts="+ bag.lastTs + " removed="+removed);
    	}
	}
    
    public int get(int key) {
        if(backingMap.containsKey(key)){
        	ValueBag vb = backingMap.get(key);
        	int value = vb.value;
        	used(key, vb.lastTs, GET, vb);
        	if(DEBUG)
        		System.out.println("\n");
        	return value;
        }
        if(DEBUG)
        	System.out.println("\n");
        return -1;
    }
    
    public void set(int key, int value) {
    	if(backingMap.containsKey(key)){    		
    		ValueBag bag = new ValueBag(key,value);
			ValueBag oldBag = backingMap.put(key,bag);
			if(DEBUG)
				System.out.println("Map contains key="+key + " lastTS="+bag.lastTs);
			used(key,oldBag.lastTs, SET, bag);
    	} else {
    		if(backingMap.size() == capacity) {
    			if(DEBUG)
    				System.out.println("Need to evict for set key="+key);
    			int evictKey = evict();
    			ValueBag bag = backingMap.remove(evictKey);
    			ValueBag newbag = new ValueBag(key,value);
    			backingMap.put(key,newbag);
    			used(key, newbag.lastTs, SET, newbag);
    			if(bag!=null)
    				gc(bag);
    		} else {
    			if(DEBUG)
    				System.out.println("No Need to evict for set key="+key);
    			ValueBag newbag = new ValueBag(key,value);
    			backingMap.put(key,newbag);
    			used(key, newbag.lastTs,SET, newbag);
    		}
    	}
    	if(DEBUG)
    		System.out.println("\n");
    }
    
   

	public String toString() {
    	return ("Keys " + backingMap);
    }
    
    public static void main(String[] args) {
    	LRUCache cache = new LRUCache(3);
    	cache.set(1,20);
    	cache.set(2,20);
    	cache.set(3,20);
    	cache.get(3);
    	cache.get(3);
    	cache.get(2);
    	cache.get(2);
    	cache.get(1);
    	cache.get(2);
    	cache.set(4,20);
    	System.out.println("Cache after operations = " + cache);    	
	}

}