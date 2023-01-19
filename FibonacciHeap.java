package fibonacciHeap;
/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
	
	// Class fields
	final static double GOLDEN_RATIO = (1 + Math.sqrt(5))/2;
	
	int size = 0;
	
	int marked = 0;
	
	int numOfTrees = 0;
	
	static int numOfLinks = 0;
	
	static int numOfCuts = 0;
	
	HeapNode min = null;
	
	HeapNode first = null;

	/**
	 * public boolean isEmpty()
	 *
	 * Returns true if and only if the heap is empty.
	 * 
	 */
	
	// O(1)
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and inserts
	 * it into the heap. The added key is assumed not to already belong to the heap.
	 * 
	 * Returns the newly created node.
	 */
	
	// O(1)
	public HeapNode insert(int key) {
		HeapNode newNode = new HeapNode(key);
		switch(size) {
		case(0): // Insert node to an empty heap
				min = newNode;
				first = newNode;
				numOfTrees = 1;
				size = 1;
				break;
		default: // Insert node at the begging of a non-empty heap
				newNode.setPrev(first.getPrev(), true);
				first.setPrev(newNode, true);
				first = newNode;
				this.setMin(newNode);
				size++;
				numOfTrees++;
		}
		return new HeapNode(key); 
	}

	/**
	 * public void deleteMin()
	 *
	 * Deletes the node containing the minimum key.
	 *
	 */
	
	// O(n)
	public void deleteMin() {
		
		// Delete the only node in a heap
		if (size == 1) {
			this.min.deleteNode();
			this.min = null;
			this.first = null;
			this.size = 0;
			this.numOfTrees = 0;
			return;
		}
		
		HeapNode deletedNodeNext = min.getNext();
		HeapNode deletedNodeChild = min.getChild();
		numOfTrees += this.min.deleteNode() - 1; // O(log(n))
		this.size--;
		
		// Set a new first node if needed
		if (this.min == this.first) {
			if (deletedNodeChild != null) {
				this.first = deletedNodeChild;
			}
			else {
				this.first = deletedNodeNext;
			}
		}
		this.min = this.first;
		HeapNode currentNode = first;
		
		/* Iterate through all roots to find the new minimum
		 * O(n) */
		do {
			this.setMin(currentNode);
			currentNode.setMark(false);
			currentNode = currentNode.getNext();
		}
		while (currentNode != first);
		
		/* Perform successive linking 
		 * O(n) */
		this.transformToAnotherHeap(this.consolidate());
	}

	/**
	 * public HeapNode findMin()
	 *
	 * Returns the node of the heap whose key is minimal, or null if the heap is
	 * empty.
	 *
	 */
	
	// O(1)
	public HeapNode findMin() {
		return this.min;
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Melds heap2 with the current heap.
	 *
	 */
	
	// O(1)
	public void meld(FibonacciHeap heap2) {
		HeapNode lastNodeFirstHeap = first.getPrev();
		HeapNode lastNodeSecondHeap = heap2.getFirst().getPrev();
		lastNodeFirstHeap.setNext(heap2.getFirst(), true);
		lastNodeSecondHeap.setNext(first, true);
		this.setMin(heap2.findMin()); // O(1)
		this.size = size + heap2.size();
	}

	/**
	 * public int size()
	 *
	 * Returns the number of elements in the heap.
	 * 
	 */
	
	// O(1)
	public int size() {
		return this.size; // should be replaced by student code
	}

	/**
	 * public int[] countersRep()
	 *
	 * Return an array of counters. The i-th entry contains the number of trees of
	 * order i in the heap. (Note: The size of of the array depends on the maximum
	 * order of a tree.)
	 * 
	 */
	public int[] countersRep() {
		int[] arr = new int[100];
		return arr; // to be replaced by student code
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap. It is assumed that x indeed belongs to the
	 * heap.
	 *
	 */
	
	// O(n)
	public void delete(HeapNode x) {
		if (x == min) {
			this.deleteMin(); // O(n)
		}
		else {
			int differenceFromMin = x.getKey() - min.getKey();
			this.decreaseKey(x, differenceFromMin + 1); // O(n)
			this.deleteMin(); // O(n)
		}
		
	}

	/**
	 * public void decreaseKey(HeapNode x, int delta)
	 *
	 * Decreases the key of the node x by a non-negative value delta. The structure
	 * of the heap should be updated to reflect this change (for example, the
	 * cascading cuts procedure should be applied if needed).
	 */
	
	// O(n)
	public void decreaseKey(HeapNode x, int delta) {
		x.setKey(x.getKey() - delta);
		if (x.getKey() < x.getParent().getKey()){
			this.cascadingCuts(x); // O(n)
		}
	}

	/**
	 * public int nonMarked()
	 *
	 * This function returns the current number of non-marked items in the heap
	 */
	
	// O(1)
	public int nonMarked() {
		return size - marked;
	}

	/**
	 * public int potential()
	 *
	 * This function returns the current potential of the heap, which is: Potential
	 * = #trees + 2*#marked
	 * 
	 * In words: The potential equals to the number of trees in the heap plus twice
	 * the number of marked nodes in the heap.
	 */
	
	// O(1)
	public int potential() {
		return numOfTrees + 2*marked; // should be replaced by student code
	}

	/**
	 * public static int totalLinks()
	 *
	 * This static function returns the total number of link operations made during
	 * the run-time of the program. A link operation is the operation which gets as
	 * input two trees of the same rank, and generates a tree of rank bigger by one,
	 * by hanging the tree which has larger value in its root under the other tree.
	 */
	
	// O(1)
	public static int totalLinks() {
		return numOfLinks; // should be replaced by student code
	}

	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made during
	 * the run-time of the program. A cut operation is the operation which
	 * disconnects a subtree from its parent (during decreaseKey/delete methods).
	 */
	
	// O(1)
	public static int totalCuts() {
		return numOfCuts; // should be replaced by student code
	}

	/**
	 * public static int[] kMin(FibonacciHeap H, int k)
	 *
	 * This static function returns the k smallest elements in a Fibonacci heap that
	 * contains a single tree. The function should run in O(k*deg(H)). (deg(H) is
	 * the degree of the only tree in H.)
	 * 
	 * ###CRITICAL### : you are NOT allowed to change H.
	 */
	public static int[] kMin(FibonacciHeap H, int k) {
		int[] arr = new int[100];
		return arr; // should be replaced by student code
	}
	
	// O(1)
	private void setMin(HeapNode otherNode) {
		if (size == 0) {
			this.min = null;
		}
		else if (otherNode.getKey() < min.getKey()) {
			min = otherNode;
		}
	}
	
	// O(1)
	public HeapNode getFirst() {
		return this.first;
	}
	
	// O(n)
	private void cascadingCuts(HeapNode cutNode) {
		HeapNode parent = cutNode.getParent();
		if (cutNode.getMark()) {
			marked--;
		}
		cutNode.cutNode();
		this.insertFirst(cutNode);
		if (parent.getMark()) {
			this.cascadingCuts(parent);
		}
		else {
			parent.setMark(true);
			marked++;
		}
	}
	
	
	// O(n)
	private FibonacciHeap consolidate() {
		HeapNode[] buckets = toBuckets(); // O(n)
		return fromBuckets(buckets); // O(log(n))
	}
	
	// O(n)
	private HeapNode[] toBuckets() {
		HeapNode[] buckets = new HeapNode[(calcLogGROfN(size))];
		first.getPrev().setNext(null, true);
		first.setPrev(null, true);
		HeapNode nextTree = first;
		HeapNode currentTree = null;
		
		/* Fill bucket array while performing successive linking 
		 * O(n) */
		do{
			currentTree = nextTree;
			nextTree = nextTree.getNext();
			int currentTreeRank = currentTree.getRank();
			while (buckets[currentTreeRank] != null) {
				currentTree = currentTree.link(buckets[currentTreeRank]);
				buckets[currentTreeRank] = null;
				currentTreeRank++;
			}
			buckets[currentTreeRank] = currentTree;
		} while (nextTree != null);
		return buckets;
	}
	
	// O(log(n))
	private FibonacciHeap fromBuckets(HeapNode[] buckets) {
		FibonacciHeap consolidatedHeap = new FibonacciHeap();
		consolidatedHeap.size = this.size;
		consolidatedHeap.marked = this.marked;
		numOfTrees = 0;
		
		// Connect roots to each other through buckets 
		this.connectBuckets(buckets); // O(log(n))
		for (int i = buckets.length - 1; i >= 0; i--) { // O(log(n))
			if (buckets[i] != null) {
				numOfTrees++;
				consolidatedHeap.insertFirst(buckets[i]); // O(1)
			}
		}
		return consolidatedHeap;
	}
	
	// O(1)
	public void insertFirst(HeapNode nodeToInsert) {
		
		// Insert into an empty list
		if (first == null) {
			first = nodeToInsert;
			min = nodeToInsert;
			numOfTrees = 1;
		}
		
		else {
			if (first.getPrev() != nodeToInsert) {
				first.getPrev().setNext(nodeToInsert, true);
			}
			first.setPrev(nodeToInsert, true);
			this.first = nodeToInsert;
			numOfTrees++;
			if (nodeToInsert.getKey() < min.getKey()) {
				this.setMin(nodeToInsert);
			}
		}
	}
	
	// O(1)
	public int calcLogGROfN(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		return (int) (Math.log(n)/Math.log(GOLDEN_RATIO));
	}
	
	// O(1)
	private void transformToAnotherHeap(FibonacciHeap otherHeap) {
		this.first = otherHeap.first;
		this.min = otherHeap.min;
		this.size = otherHeap.size;
		this.marked = otherHeap.marked;
		this.numOfTrees = otherHeap.numOfTrees;
	}
	
	// O(log(n))
	private void connectBuckets(HeapNode[] buckets) {
		int firstNonNullIndex = -1;
		int lastNonNullIndex = 0;
		for (int i = 0; i < buckets.length; i++) { // O(log(n))
			boolean noMoreNonNullIndexs = false;
			if (buckets[i] != null) {
				if (firstNonNullIndex == -1) {
					firstNonNullIndex = i;
					lastNonNullIndex = i;
				}
				for (int j = i + 1; j < buckets.length; j++) {
					if (buckets[j] != null) {
						buckets[i].setNext(buckets[j], true);
						i = j - 1;
						lastNonNullIndex = j;
						break;
					}
					else if(j == buckets.length -1) {
						noMoreNonNullIndexs = true;
						lastNonNullIndex = i;
						break;
					}
				}
				if (noMoreNonNullIndexs) {
					break;
				}
			}
		}
		
		buckets[lastNonNullIndex].setNext(buckets[firstNonNullIndex], true);
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than FibonacciHeap (for example
	 * HeapNode), do it in this file, not in another file.
	 * 
	 */
	public static class HeapNode {
		
		// Class fields
		public int key;
		
		private int rank = 0;
		
		private boolean mark = false;
		
		private HeapNode parent = null;
		
		private HeapNode child = null;

		private HeapNode next = this;

		private HeapNode prev = this;
		
		// Constructor
		public HeapNode(int key) {
			this.key = key;
		}
		
		// O(log(n))
		public int deleteNode() {
			int numOfNewTrees = 0;
			if (this.child != null) {
				HeapNode currChild = this.child;
				
				/* Insert children into the heap 
				 * O(log(n)) */
				do {
					currChild.setParent(this.parent, true);
					currChild = currChild.getNext();
					numOfNewTrees++;
				}while(currChild != this.child);
				if (this.next != this) {
					this.next.setPrev(this.child.getPrev(), true);
					this.prev.setNext(this.child, true);
				}
			}
			else {
				this.next.setPrev(prev, true);
			}
			this.parent = null;
			this.next = null;
			this.prev = null;
			this.child = null;
			return numOfNewTrees;
		}

		// O(1)
		public void cutNode() {
			numOfCuts++;
			if (this == this.parent.getChild()) {
				this.fixParentRanks();
			}
			this.mark = false;
			if (this.next == this) {
				this.parent.setChild(null, true);
			}
			else {
				if (this.parent.getChild() == this) {
					this.parent.setChild(this.next, false);
				}
				this.next.setPrev(this.prev, true);
			}
			this.parent = null;
		}
		
		private void fixParentRanks() {
			HeapNode currParent = this.parent;
			while (currParent != null) {
				currParent.setRank(currParent.getRank() - this.rank - 1);
				currParent = currParent.getParent();
			}
		}
		
		// O(1)
		public HeapNode link(HeapNode otherNode) {
			numOfLinks++;
			HeapNode smallerNode;
			HeapNode largerNode;
			if (otherNode.getKey() < this.getKey()) {
				smallerNode = otherNode;
				largerNode = this;
			}
			else {
				smallerNode = this;
				largerNode = otherNode;
			}
			if (smallerNode.getChild() != null) {
				largerNode.setPrev(smallerNode.getChild().getPrev(), true);
				largerNode.setNext(smallerNode.getChild(), true);
			}
			else {
				largerNode.setPrev(largerNode, true);
			}
			smallerNode.setChild(largerNode, true);
			smallerNode.setRank(smallerNode.rank + 1);
			smallerNode.setNext(smallerNode, true);
			smallerNode.setPrev(smallerNode, true);
			return smallerNode;	
		}
		
		/* Getters and setters 
		 * all O(1) */
	
		public void setKey(int key) {
			this.key = key;
		}

		public int getKey() {
			return this.key;
		}
		
		public void setRank(int rank) {
			this.rank = rank;
		}
		
		public int getRank() {
			return this.rank;
		}
		
		public void setMark(boolean mark) {
			this.mark = mark;
		}
		
		public boolean getMark() {
			return this.mark;
		}
		
		public void setParent(HeapNode parent, boolean firstCall) {
			this.parent = parent;
			if (parent != null && firstCall) {
				parent.setChild(this, false);
			}
		}
		
		public HeapNode getParent() {
			return this.parent;
		}
		
		public void setChild(HeapNode child, boolean firstCall) {
			this.child = child;
			if (child != null && firstCall) {
				child.setParent(this, false);
			}
		}
		
		public HeapNode getChild() {
			return this.child;
		}
		
		public void setNext(HeapNode next, boolean firstCall) {
			this.next = next;
			if (next != null && firstCall) {
				next.setPrev(this, false);
			}
		}
		
		public HeapNode getNext() {
			return this.next;
		}
		
		public void setPrev(HeapNode prev, boolean firstCall) {
			this.prev = prev;
			if (prev != null && firstCall) {
				prev.setNext(this, false);
			}
		}
		
		public HeapNode getPrev() {
			return this.prev;
		}
	}
}
