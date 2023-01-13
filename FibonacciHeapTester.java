package fibonacciHeap;

import java.util.HashMap;

import java.util.Random;


public class FibonacciHeapTester {
	public static void main(String[] args) {

		FibonacciHeap fibHeap = new FibonacciHeap();
		
		HashMap<Integer, Boolean> seenInts 
		= new HashMap<Integer, Boolean>();
		
		for (int i = 0; i < 50; i++) {
			Random r = new Random();
			int newKey = Math.abs(r.nextInt(1, 100));
			while(seenInts.containsKey(newKey)) {
				newKey = Math.abs(r.nextInt(1, 100));
			}
			seenInts.put(newKey, true);
			fibHeap.insert(newKey);
		}
		
		fibHeap.deleteMin();
		
		System.out.println(fibHeap.size);
	}

}
