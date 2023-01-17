package fibonacciHeap;

import java.util.HashMap;

import java.util.Random;

import fibonacciHeap.FibonacciHeap.HeapNode;

// [81, 65, 18, 66, 71, 56, 77, 30, 31, 79]

public class FibonacciHeapTester {
	public static void main(String[] args) {

		FibonacciHeap fibHeap = new FibonacciHeap();
		
		HashMap<Integer, Boolean> seenInts 
		= new HashMap<Integer, Boolean>();
		
		//int[] nums = new int[] {81, 65, 18, 66, 71, 56, 77, 30, 31, 3};
		
		for (int i = 0; i < 50; i++) {
			Random r = new Random();
			int newKey = Math.abs(r.nextInt(1, 100));
			while(seenInts.containsKey(newKey)) {
				newKey = Math.abs(r.nextInt(1, 100));
			}
			seenInts.put(newKey, true);
			fibHeap.insert(newKey);
			// fibHeap.insert(nums[i]);
		}
		
		fibHeap.deleteMin();
		
		fibHeap.delete(fibHeap.getFirst().getNext().getChild().getNext());
		
		fibHeap.deleteMin();
		
		FibonacciHeapTester.printHeap(fibHeap.getFirst());
		
	}
	
	public static void printHeap(HeapNode node) {
		HeapNode currNode = node;
		do {
			System.out.println(currNode.getKey());
			HeapNode midCurrNode = currNode.getChild();
			if (midCurrNode != null) {
				System.out.println(currNode.getKey() + "|" + midCurrNode.getKey());
				do {
					printHeap(midCurrNode);
				}while(midCurrNode != midCurrNode);
			}
			System.out.println(currNode.getKey() + "--->" + currNode.getNext().getKey());
			currNode = currNode.getNext();
		}while(currNode != node);
	}
}
