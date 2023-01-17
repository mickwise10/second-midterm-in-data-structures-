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
		
		for (int i = 0; i < 10; i++) {
			Random r = new Random();
			int newKey = Math.abs(r.nextInt(1, 100));
			while(seenInts.containsKey(newKey)) {
				newKey = Math.abs(r.nextInt(1, 100));
			}
			seenInts.put(newKey, true);
			fibHeap.insert(newKey);
			// fibHeap.insert(nums[i]);
		}
		
		FibonacciHeapTester.printHeap(fibHeap.getFirst());
		
		System.out.println("----------------------------");
		
		fibHeap.deleteMin();
		
		FibonacciHeapTester.printHeap(fibHeap.getFirst());
		
		System.out.println("----------------------------");
		
		HeapNode nodeToBeDeleted = fibHeap.getFirst().getNext().getChild().getNext();
		
		System.out.println(nodeToBeDeleted.getKey());
		
		fibHeap.delete(fibHeap.getFirst().getNext().getChild().getNext());
				
		System.out.println("----------------------------");
		
		FibonacciHeapTester.printHeap(fibHeap.getFirst());
		
		System.out.println("----------------------------");

		fibHeap.deleteMin();
		
		System.out.println("----------------------------");
		
		FibonacciHeapTester.printHeap(fibHeap.getFirst());
		
		System.out.println("----------------------------");
		
		System.out.println(fibHeap.size());
		
	}
	
	public static void printHeap(HeapNode node) {
		HeapNode currNode = node;
		do {
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
