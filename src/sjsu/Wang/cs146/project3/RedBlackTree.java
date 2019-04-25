package sjsu.Wang.cs146.project3;
/**
 * Red Black Tree containing a node class that takes in a String as its parameter
 * @author Thomas Wang
 *
 * @param <Key>
 */
public class RedBlackTree<Key extends Comparable<Key>> {
	//Reference to the root of the tree. Used to traverse down or help in other method calls
	public static RedBlackTree.Node<String> root;

	//Node class provides the String key that is comparable
	//with other keys, as well as includes parent, and left/right child pointers.
	public static class Node<Key extends Comparable<Key>> { // changed to static

		Key key;
		Node<String> parent;
		Node<String> leftChild;
		Node<String> rightChild;
		int color = 0; // red - 0, black - 1

		public Node(Key data) {
			this.key = data;
			leftChild = null;
			rightChild = null;
		}

		public int compareTo(Node<Key> n) { // this < that <0
			return key.compareTo(n.key); // this > that >0
		}

		//checks if the node is a leaf
		public boolean isLeaf() {
			if (this.equals(root) && this.leftChild == null && this.rightChild == null)
				return true;
			if (this.equals(root))
				return false;
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}
	}

	//Construction of an empty tree
	public RedBlackTree() {
		root = null;
	}

	public interface Visitor<Key extends Comparable<Key>> {
		/**
		 * This method is called at each node.
		 * 
		 * @param n the visited node
		 */
		void visit(Node<Key> n);
	}

	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}

	public void printTree() { // preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;
		printTree(currentNode);
	}

	public void printTree(RedBlackTree.Node<String> node) {
		System.out.print(node.key);
		if (node.isLeaf()) {
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}

	// place a new node in the RB tree with data the parameter and color it red.
	public void addNode(String data) { // this < that <0. this >
									   // that >0
		//Uses a boolean value to keep iterating to check which side the 
		//tree should add the node into
		boolean found = false;
		RedBlackTree.Node<String> node = new RedBlackTree.Node<String>(data);
		RedBlackTree.Node<String> temp = root;
		//empty root
		if (root == null) {
			root = node;
		} 
		//everywhere else in the tree
		else {
			while (!found) {
				//go left
				if (data.compareTo(temp.key) < 0) {
					//empty slot in left child
					if (temp.leftChild == null) {
						//sets pointers and recursively fixes the tree
						temp.leftChild = node;
						node.parent = temp;
						found = true; //ends the while loop
					} 
					//continue recursively trying to find the correct spot
					else {
						temp = temp.leftChild;
					}
				} 
				//go right
				else if (data.compareTo(temp.key) > 0) {
					//empty slot in right child
					if (temp.rightChild == null) {
						// sets pointers and recursively fixes the tree
						temp.rightChild = node;
						node.parent = temp;
						found = true; //ends while loop
					} 
					//continue recursively trying to find the correct spot
					else {
						temp = temp.rightChild;
					}
				}
			}
		}
		//fix tree with recoloring and pointers
		fixTree(node);
	}
	
	public void insert(String data) {
		addNode(data);
	}

	//returns true if the value it's looking up is stored within the tree somewhere
	public boolean lookup(String data) {
		//uses a while loop to iteratively traverse down
		boolean found = false;
		RedBlackTree.Node<String> temp = root;
		while(!found) {
			if(data.toLowerCase().equals(data)) {
				found = true;
				break;
			}
			//go left
			else if(data.toLowerCase().compareTo(temp.key) < 0) {
				temp = temp.leftChild;
			}
			//go right
			else if(data.toLowerCase().compareTo(temp.key) > 0) {
				temp = temp.rightChild;
			}
		}
		return found;
	}

	/**
	 * Returns sibling
	 * @param node
	 * @return
	 */
	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> node) {
		RedBlackTree.Node<String> result = null;
		// checks to to see if the node is the left child, return right child
		if (node.compareTo(node.parent) < 0) {
			result = node.parent.rightChild;
		}
		// checks to see if node is right child, returns left child
		else if (node.compareTo(node.parent) > 0) {
			result = node.parent.leftChild;
		}
		return result;
	}

	/**
	 * Returns the aunt of the current node
	 * @param node
	 * @return
	 */
	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> node) {
		RedBlackTree.Node<String> result = null;
		if (node != root && node.parent != root) {
			if (node.parent.parent.leftChild != null && node.parent == node.parent.parent.rightChild) {
				result = node.parent.parent.leftChild;
			} 
			else {
				result = node.parent.parent.rightChild;
			}
		}
		return result;
	}
	/**
	 * Returns the grandparent of the current node
	 * @param node
	 * @return
	 */
	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> node) {
		RedBlackTree.Node<String> result = null;
		if (node != root && node.parent != root) {
			result = node.parent.parent;
		}
		return result;
	}

	/**
	 * Get root
	 * @return
	 */
	public RedBlackTree.Node<String> getRoot() {
		return root;
	}

	/**
	 * Handles the a left rotation of the tree, fixing pointers
	 * X gets new children A and B
	 * Y gets new children X and C
	 * This method along with the right rotation
	 * is mostly restructuring the pointers of the nodes that are being rotated.
	 * 				
	 * 				Y					X
	 * 			  /   \				  /   \	
	 *           X     C      <-     A     Y
	 *          / \   				      / \
	 *         A   B 					 B   C
	 *			
	 * 
	 * @param node
	 */
	public void rotateLeft(RedBlackTree.Node<String> node) {
		//
		RedBlackTree.Node<String> y = node.rightChild;
		node.rightChild = y.leftChild;
		if (y.leftChild != null) {
			y.leftChild.parent = node;
		}
		y.parent = node.parent;
		if (node.parent == null) {
			root = y;
		} 
		else {
			if (node == node.parent.leftChild) {
				node.parent.leftChild = y;
			} 
			else {
				node.parent.rightChild = y;
			}
		}
		y.leftChild = node;
		node.parent = y;
	}

	/**
	 * Handles the a left rotation of the tree, fixing pointers
	 * X gets new children A and Y
	 * Y gets new children B and C
	 * 				
	 * 				Y					X
	 * 			  /   \				  /   \	
	 *           X     C      ->     A     Y
	 *          / \   				      / \
	 *         A   B 					 B   C
	 *			
	 * 
	 * @param node
	 */
	public void rotateRight(RedBlackTree.Node<String> node) {
		RedBlackTree.Node<String> x = node.leftChild;
		node.leftChild = x.rightChild;
		if (x.rightChild != null) {
			x.rightChild.parent = node;
		}
		x.parent = node.parent;
		if (node.parent == null) {
			root = x;
		} 
		else {
			if (node == node.parent.leftChild) {
				node.parent.leftChild = x;
			} 
			else {
				node.parent.rightChild = x;
			}
		}
		x.rightChild = node;
		node.parent = x;
	}

	/**
	 * Checks to see if the node is the left Child of its parent
	 * @param node
	 * @return
	 */
	public boolean isLeftChild(RedBlackTree.Node<String> node) {
		boolean left = false;
		if (node != root && node == node.parent.leftChild) {
			left = true;
		}
		return left;
	}

	/**
	 * Checks to see if the node is the right Child of its parent
	 * @param node
	 * @return
	 */
	public boolean isRightChild(RedBlackTree.Node<String> node) {
		boolean right = false;
		if (node != root && node == node.parent.rightChild) {
			right = true;
		}
		return right;
	}

	/**
	 * Recolors and restructures the tree
	 * @param current
	 */
	public void fixTree(RedBlackTree.Node<String> current) {
		//inserted node's color is red
		current.color = 0;
		
		//Restores red-black property by iteratively traversing bottom
		//up to fix the coloring and restructuring
		//1) If current is the root, skip the loop and color the root black, quit
		//2) If the current's parent is black, skip the loop
		//3) Enter loop if the current node is not the root, and the parent's color is red
		while ((current != root) && (current.parent.color == 0)) {
			//parent is a left child
			//A) parent is left child
			if (isLeftChild(current.parent)) {
				RedBlackTree.Node<String> aunt = getAunt(current);
				//I)Aunt is empty
				if (aunt != null && aunt.color == 0) {
					current.parent.color = 1;
					aunt.color = 1;
					getGrandparent(current).color = 0;
					current = getGrandparent(current);
				} 
				//II) aunt is black
				else {
					//parent is left child, current is right child
					if (isRightChild(current)) {
						current = current.parent;
						rotateLeft(current);
					}
					current.parent.color = 1;
					getGrandparent(current).color = 0;
					rotateRight(getGrandparent(current));
				}
			} 
			//parent is right child
			else {
				RedBlackTree.Node<String> aunt = getAunt(current);
				//I) Aunt is empty 
				if (aunt != null && aunt.color == 0) {
					current.parent.color = 1;
					aunt.color = 1;
					getGrandparent(current).color = 0;
					current = getGrandparent(current);
				} 
				//II) Aunt is black
				else {
					//parent is right child, current is left child
					if (isLeftChild(current)) {
						current = current.parent;
						rotateRight(current);
					}
					current.parent.color = 1;
					getGrandparent(current).color = 0;
					rotateLeft(getGrandparent(current));
				}
			}
		}
		//set root to be black
		root.color = 1;
	}

	/**
	 * Checks if the node's key is empty
	 * @param node
	 * @return
	 */

	public void preOrderVisit(Visitor<String> v) {
		preOrderVisit(root, v);
	}

	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}
}
