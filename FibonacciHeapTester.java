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
		
		// int[] nums = {602, 261, 357, 10, 878, 729, 574, 809, 634, 334, 223, 491, 449, 371, 333, 776, 57, 524, 674, 446};
		// int[] nums = {69, 21, 87, 96};

		
		for (int j = 0; j < 5; j++) {
			Random r = new Random();
			for (int i = 0; i < Math.abs(r.nextInt(3, 1000)); i++) {
				Random r1 = new Random();
				int newKey = Math.abs(r1.nextInt(1, 1000));
				while(seenInts.containsKey(newKey)) {
					newKey = Math.abs(r1.nextInt(1, 1000));
				}
				seenInts.put(newKey, true);
				fibHeap.insert(newKey);
				//fibHeap.insert(nums[nums.length - i - 1]);
			}
		
		
			FibonacciHeapTester.printHeap(fibHeap.getFirst());
			
			System.out.println("----------------------------");
			
			System.out.println(fibHeap.size);
			
			System.out.println("----------------------------");

			System.out.println(fibHeap.min.getKey());

			System.out.println("----------------------------");
			
			fibHeap.deleteMin();
			
			FibonacciHeapTester.printHeap(fibHeap.getFirst());
			
			System.out.println("----------------------------");
			
			HeapNode nodeToBeDeleted = fibHeap.getFirst().getNext().getNext().getChild().getChild().getNext();
						
			System.out.println(nodeToBeDeleted.getKey());
						
			fibHeap.delete(nodeToBeDeleted);			
			
			System.out.println("----------------------------");

			FibonacciHeapTester.printHeap(fibHeap.getFirst());
			
			System.out.println("----------------------------");
	
			fibHeap.deleteMin();
						
			FibonacciHeapTester.printHeap(fibHeap.getFirst());
			
			System.out.println("----------------------------");
			
			System.out.println(fibHeap.marked);
			
			System.out.println("----------------------------");
			
			}
	}
	
	public static void printHeap(HeapNode node) {
		HeapNode currNode = node;
		if (node == null) {
			System.out.println("Heap is empty!");
			return;
		}
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
	
//	public static void printHeap1(HeapNode node) {
//		HeapNode currNode = node;
//		if (node == null) {
//			// System.out.println("Heap is empty!");
//			return;
//		}
//		do {
//			HeapNode midCurrNode = currNode.getChild();
//			if (midCurrNode != null) {
//				// System.out.println(currNode.getKey() + "|" + midCurrNode.getKey());
//				do {
//					printHeap1(midCurrNode);
//				}while(midCurrNode != midCurrNode);
//			}
//			// System.out.println(currNode.getKey() + "--->" + currNode.getNext().getKey());
//			currNode = currNode.getNext();
//		}while(currNode != node);
//	}
}
