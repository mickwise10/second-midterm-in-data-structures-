package fibonacciHeap;
/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
	
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
	public HeapNode insert(int key) {
		HeapNode newNode = new HeapNode(key);
		switch(size) {
		case(0):
				min = newNode;
				first = newNode;
				numOfTrees = 1;
				size = 1;
				break;
		default:
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
	public void deleteMin() {
		HeapNode deletedNodePrev = min.getPrev();
		HeapNode deletedNodeNext = min.getNext();
		HeapNode deletedNodeChild = min.getChild();
		if (deletedNodeChild != null) {
			HeapNode childPrev = deletedNodeChild.getPrev();
			deletedNodeNext.setPrev(childPrev, true);
			deletedNodePrev.setNext(deletedNodeChild, true);

		}
		min.deleteNode();
		size--;
		this.first = deletedNodeNext;
		min = first;
		HeapNode currentNode = first;
		do {
			this.setMin(currentNode);
			currentNode = currentNode.getNext();
		}
		while (currentNode != first);
		this.transformToAnotherHeap(this.consolidate());
	}

	/**
	 * public HeapNode findMin()
	 *
	 * Returns the node of the heap whose key is minimal, or null if the heap is
	 * empty.
	 *
	 */
	public HeapNode findMin() {
		return this.min;
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Melds heap2 with the current heap.
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		HeapNode lastNodeFirstHeap = first.getPrev();
		HeapNode lastNodeSecondHeap = heap2.getFirst().getPrev();
		lastNodeFirstHeap.setNext(heap2.getFirst(), true);
		lastNodeSecondHeap.setNext(first, true);
		this.setMin(heap2.findMin());
		this.size = size + heap2.size();
	}

	/**
	 * public int size()
	 *
	 * Returns the number of elements in the heap.
	 * 
	 */
	public int size() {
		return this.size(); // should be replaced by student code
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
	public void delete(HeapNode x) {
		if (x == min) {
			this.deleteMin();
		}
		else {
			int differenceFromMin = x.getKey() - min.getKey();
			this.decreaseKey(x, differenceFromMin + 1);
			this.deleteMin();
			x.setRank(x.getRank() + differenceFromMin + 1);
		}
		
	}

	/**
	 * public void decreaseKey(HeapNode x, int delta)
	 *
	 * Decreases the key of the node x by a non-negative value delta. The structure
	 * of the heap should be updated to reflect this change (for example, the
	 * cascading cuts procedure should be applied if needed).
	 */
	public void decreaseKey(HeapNode x, int delta) {
		x.setKey(x.getKey() - delta);
		if (x.getKey() < x.getParent().getKey()){
			this.cascadingCuts(x);
		}
	}

	/**
	 * public int nonMarked()
	 *
	 * This function returns the current number of non-marked items in the heap
	 */
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
	
	private void setMin(HeapNode otherNode) {
		if (otherNode.getKey() < min.getKey()) {
			min = otherNode;
		}
	}
	
	public HeapNode getFirst() {
		return this.first;
	}
	
	private void cascadingCuts(HeapNode cutNode) {
		HeapNode parent = cutNode.getParent();
		if (cutNode.getMark()) {
			marked--;
		}
		cutNode.cutNode();
		this.insertFirst(cutNode, true);
		numOfCuts++;
		numOfTrees++;
		cutNode.setMark(false);
		if (parent.getMark()) {
			this.cascadingCuts(parent);
		}
		else {
			parent.setMark(true);
		}
	}
	
	private FibonacciHeap consolidate() {
		HeapNode[] buckets = toBuckets();
		return fromBuckets(buckets);
	}
	
	private HeapNode[] toBuckets() {
		HeapNode[] buckets = new HeapNode[(calcLogGROfN() + 1)];
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = null;
		}
		HeapNode nextTree = first;
		HeapNode currentTree = null;
		do{
			currentTree = nextTree;
			nextTree = nextTree.getNext();
			int currentTreeRank = currentTree.getRank();
			while (buckets[currentTreeRank] != null) {
				currentTree = currentTree.link(buckets[currentTreeRank]);
				buckets[currentTreeRank] = null;
				currentTreeRank = currentTree.getRank();
			}
			buckets[currentTree.getRank()] = currentTree;
		} while (nextTree != first);
		return buckets;
	}
	
	private FibonacciHeap fromBuckets(HeapNode[] buckets) {
		FibonacciHeap consolidatedHeap = new FibonacciHeap();
		numOfTrees = 0;
		for (int i = buckets.length - 1; i >= 0; i--) {
			if (buckets[i] != null) {
				numOfTrees++;
				consolidatedHeap.insertFirst(buckets[i], false);
			}
		}
		return consolidatedHeap;
	}
	
	public void insertFirst(HeapNode nodeToInsert, boolean insertedFromCasCuts) {
		if (first == null) {
			first = nodeToInsert;
			min = nodeToInsert;
			numOfTrees = 1;
			size = nodeToInsert.getRank() + 1;
		}
		else {
			first.getPrev().setNext(nodeToInsert, true);
			first.setPrev(nodeToInsert, true);
			this.first = nodeToInsert;
			numOfTrees++;
			if (nodeToInsert.getKey() < min.getKey()) {
				this.setMin(nodeToInsert);
			}
			if (!insertedFromCasCuts) {
				size += nodeToInsert.getRank() + 1;
			}
		}
	}
	
	public int calcLogGROfN() {
		return (int) (Math.log(size)/Math.log(GOLDEN_RATIO));
	}
	
	private void transformToAnotherHeap(FibonacciHeap otherHeap) {
		this.first = otherHeap.first;
		this.min = otherHeap.min;
		this.size = otherHeap.size;
		this.marked = otherHeap.marked;
		this.numOfTrees = otherHeap.numOfTrees;
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
		
		public HeapNode(int key) {
			this.key = key;
		}
		
		public void deleteNode() {
			if (parent != null) {
				parent.setChild(child, true);
			}
			if (child != null) {
				child.setParent(parent, true);
			}
			next.setPrev(prev, true);
			parent = null;
			next = null;
			prev = null;
			child = null;
		}
		
		public void cutNode() {
			this.parent.setRank(this.parent.getRank() - 1);
			this.parent = null;
			this.mark = false;
			if (this.next == this) {
				this.parent.setChild(null, true);
			}
			else {
				this.parent.setChild(this.next, true);
				this.next.setPrev(this.prev, true);
			}
			
		}
		
		public HeapNode link(HeapNode otherNode) {
			numOfLinks++;
			if (otherNode.getKey() < this.key) {
				otherNode.setNext(this.next, true);
				otherNode.setPrev(this.prev, true);
				if (otherNode.getChild() != null) {
					otherNode.getChild().setParent(null, true);
					this.setPrev(otherNode.getChild().getPrev(), true);
					this.setNext(otherNode.getChild().getNext(), true);
				}
				else {
					this.setPrev(this, true);
				}
				otherNode.setChild(this, true);
				otherNode.setRank(otherNode.getRank() + this.rank + 1);
				return otherNode;
			}
			else {
				this.setNext(otherNode.getNext(), true);
				this.setPrev(otherNode.getPrev(), true);
				if (child != null) {
					this.child.setParent(null, true);
					otherNode.setPrev(this.child.getPrev(), true);
					otherNode.setNext(this.child, true);
				}
				else {
					otherNode.setPrev(otherNode, true);
				}
				this.setChild(otherNode, true);
				this.setRank(this.rank + otherNode.getRank() + 1);
				return this;
			}			
		}
		
		// Getters and setters
	
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
