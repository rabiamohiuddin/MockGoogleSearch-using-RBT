
// Enum Color defines the two types of Colors possible for a Node in Red Black Tree
enum Color {
		RED, BLACK;
}

public class RBTree {

		public static Node nil = new Node(Color.BLACK);			// Nil black node that root and all leaf nodes point to
		private Node root;		// RBT has one variable, root, to keep track of all other nodes

		/**
		 * RBTree constructor that set's the root color and points root's parent and children to nil
		 * 
		 * @param none
		 * @return none
		 */
		public RBTree() {
			this.root = nil;		// Root points to nil
			this.root.left = this.root.right = this.root.parent = nil;		// Children and Parent point to nil node
			this.root.color = Color.BLACK;		// Root is always black
		}

		/**
		 * Get root Node object
		 * 
		 * @param none
		 * @return Node root
		 */
		public Node getRoot() {
			return root;
		}

		/**
		 * RBT in order walk that prints smallest to largest by going through the left side of tree first, then right side
		 * 
		 * @param Node
		 *              node
		 * @return none
		 */
		public void inorderRBTreeWalk(Node node) {
			if (node != nil) {		// As long as the node is populated
					inorderRBTreeWalk(node.left);		// Iterate through right side first to display largest values first
					System.out.format("%-15s%-15s%-15s%-20s%-15s\n", node.key.getRanking(), node.key.getTotalScore(), node.key.getIndex(), node.color, node.key.getName());
					inorderRBTreeWalk(node.right);		// Iterate through left side of tree
			}
		}

		/**
		 * RBT pre order walk that iterates through tree and prints key, left, right 
		 * 
		 * @param Node
		 *              node
		 * @return none
		 */
		public void preorderRBTreeWalk(Node node) {
			if (node != nil) {		// As long as the node is populated
					System.out.format("%-15s%-15s%-15s%-20s%-15s\n", node.key.getRanking(), node.key.getTotalScore(), node.key.getIndex(), node.color, node.key.getName());
					preorderRBTreeWalk(node.left);		// Iterate through left side first
					preorderRBTreeWalk(node.right);		// Iterate through right side of tree
			}
		}

		/**
		 * RBT smallest node in tree
		 * 
		 * @param Node
		 *              node
		 * @return Node -> smallest node in tree
		 */
		public Node treeMinimum(Node node) {
			while (node.left != nil) {		// Smallest node is located all the way bottom to the left
					node = node.left;			// Keep going left
			}
			return node;			// Return left node that has no more left children
		}

		/**
		 * RBT largest node in tree
		 * 
		 * @param Node
		 *              node
		 * @return Node -> largest node in tree
		 */
		public Node treeMaximum(Node node) {
			while (node.right != nil) {		// Largest node is located all the way bottom to the left
					node = node.right;		// Keep going right
			}
			return node;			// Return right node that has no more right children
		}

		/**
		 * Rotates left around a given node
		 * 
		 * @param Node
		 *              node
		 * @return none
		 */
		public void leftRotate(Node node) {
			Node y = node.right;			// Hold the node's right child
			node.right = y.left;			// Set the node's right child to its child's left
			if (y.left != nil) {		// If left child points to nil
					y.left.parent = node;		// Set left's parent to node
			}
			y.parent = node.parent;		// Set y parent to node parent
			if (node.parent == nil) {		// If node is the root of the tree
					this.root = y;			// Set the root to y
			} else if (node == node.parent.left) {		// If the node is a left child
					node.parent.left = y;			// Set node's left child to y
			} else {			// If node is a right child
					node.parent.right = y;		// Set node's right child to y
			}
			y.left = node;			// Set y left child to node
			node.parent = y;		// Node's parent is y
		}

		/**
		 * Rotates right around a given node
		 * 
		 * @param Node
		 *              node
		 * @return none
		 */
		public void rightRotate(Node node) {
			Node y = node.left;		// Hold the node's left child
			node.left = y.right;		// Set node's left child to its left child's right child
			if (y.right != nil)        			// If y has a non null right child
					y.right.parent = node;		// Set y right child's parent to point to node
			y.parent = node.parent;			// Set Y parent to point to node's parent
			if (node.parent == nil)        		// If node is the root of the tree
					root = y;			// root is now Y
			else if (node == node.parent.left)        		// If node is a left child
					node.parent.left = y;			// Set node's left child to Y
			else {				// If node is a right child
					y.parent.right = y;			// Set node's right child to Y
			}
			y.right = node;			// Set Y right to node
			node.parent = y;			// Need to set node's parent to Y
		}

		/**
		 * Inserts a new node into RB Tree while maintaining RB properties. Updates ranking of each node
		 * 
		 * @param Node
		 *              newNode
		 * @return none
		 */
		public void RBTreeInsert(Node newNode) {
			Node parent = nil;			// Parent of root is null
			Node iterator = getRoot();		// Start iterator at root of tree
			while (iterator != nil) {			// Iterate through nodes till find null
					parent = iterator;			// Store parent to compare new node with children
					if (newNode.compareTo(iterator) == -1) {		// If new node is less than iterator
						iterator = iterator.left;			// Go to left subtree
					} else {			// If new node is greater than iterator
						iterator = iterator.right;			// Go to right subtree
					}
			}
			newNode.parent = parent;		// New node's parent is parent of null node
			if (parent == nil) {				// If Tree was empty
					this.root = newNode;			// Set root of tree to newNode
			} else if (newNode.compareTo(parent) == -1) {		// If newNode is less than its parent
					parent.left = newNode;			// Set parent's left node to newNode
			} else {					// If newNode is greater than its parent
					parent.right = newNode;			// Set parent;s right node to newNode
			}
			newNode.left = nil;		// Set left child to point to nil
			newNode.right = nil;		// Set right child to point to nil
			newNode.color = Color.RED;		// New node is initially set to Red
			RBInsertFixup(newNode);			// Call fix up to ensure maintains all RB Tree properties
			updateRanking();		// Update ranking of each node
		}

		/**
		 * Fixes the RB Tree to ensure it maintains all RB Tree properties after Insertion
		 * 
		 * @param Node
		 *              newNode
		 * @return none
		 */
		public void RBInsertFixup(Node newNode) {
			Node y;			// Node placeholder
			while ((newNode.parent != nil) && (newNode.parent.color == Color.RED)) {		// While the node is a child and its parent is red
					if (newNode.parent == newNode.parent.parent.left) {			// If the node's parent is a left child
						y = newNode.parent.parent.right;			// Hold node's right grandchild
						// CASE 1
						if (y.color == Color.RED) {			// If right grandchild is red
								newNode.parent.color = Color.BLACK;		// Set Y's sibling to Black
								y.color = Color.BLACK;		// Set Y color to Black
								newNode.parent.parent.color = Color.RED;		// Set grandfather to Red
								newNode = newNode.parent.parent;			// Set new node to its grandparent
						} else {		// If right grandchild is black
								// CASE 2
								if (newNode == newNode.parent.right) {		// If new node is a right child
									newNode = newNode.parent;			// Set new node to its parent
									leftRotate(newNode);			// Left Rotate around new node
								}
								// CASE 3
								newNode.parent.color = Color.BLACK;			// Set parents color to Black
								newNode.parent.parent.color = Color.RED;		// Set grandfather to Red
								rightRotate(newNode.parent.parent);			// Right rotate about grandfather
						}

					} else {			// If the node's parent is a right child
						y = newNode.parent.parent.left;			// Hold node's left grandchild
						// CASE 1
						if (y.color == Color.RED) {			// If left grandchild is red
								newNode.parent.color = Color.BLACK;		// Set Y's sibling to Black
								y.color = Color.BLACK;		// Set Y color to Black
								newNode.parent.parent.color = Color.RED;		// Set grandfather to Red
								newNode = newNode.parent.parent;			// Set new node to its grandparent
						} else {		// If left grandchild is black
								// CASE 2
								if (newNode == newNode.parent.left) {		// If new node is a left child
									newNode = newNode.parent;			// Set new node to its parent
									rightRotate(newNode);			// Right Rotate around new node
								}
								// CASE 3
								newNode.parent.color = Color.BLACK;			// Set parents color to Black
								newNode.parent.parent.color = Color.RED;		// Set grandfather to Red
								leftRotate(newNode.parent.parent);			// Left rotate about grandfather
						}
					}
			}
			this.root.color = Color.BLACK;			// Set root of tree to Black (RB Tree property)
		}

		/**
		 * Transplant subtrees of two given nodes
		 * 
		 * @param Node
		 *              u, Node v
		 * @return none
		 */
		public void RBTransplant(Node u, Node v) {
			if (u.parent == nil) {		// If u is the root of the tree
					root = v;			// Set root to v
			} else if (u == u.parent.left) {		// If u is the left child
					u.parent.left = v;			// Set left child to v
			} else {							// If u is the right child
					u.parent.right = v;		// Set right child to v
			}
			v.parent = u.parent;		// Set v's parent to u's parent
		}

		/**
		 * Deletes a given node from RBT while maintaining RBT properties. Updates ranking of each node
		 * 
		 * @param Node
		 *              delete
		 * @return none
		 */
		public void RBTreeDelete(Node delete) {
			Node min = delete;		// Set node placeholder to node want to delete
			Color minOriginalColor = min.color;			// Store it's original color
			Node x;			// Node placeholder x
			if (delete.left == nil) {		// If node want to delete doesn't have a left child
					x = delete.right;			// Set x to delete's right child
					RBTransplant(delete, delete.right);		// Transplant delete and delete's right subtree
			} else if (delete.right == nil) {			// If delete node doesnt have a right child
					x = delete.left;			// Set x to delete's left child
					RBTransplant(delete, delete.left);		// Transplant delete and delete's left subtree
			} else {			// If has both left and right children
					min = treeMinimum(delete.right);		// Find smallest node in right subtree
					minOriginalColor = min.color;			// Store minimum node's original color
					x = min.right;				// Set x to min's right child
					if (min.parent == delete) {			// If delete is a child of min
						x.parent = min;			// Set x parent to min
					} else {			// If delete is not a child of min
						RBTransplant(min, min.right);		// Transplant min and min's right subtree
						min.right = delete.right;		// Set min's right node to delete's right node
						min.right.parent = min;		// Set min's right's parent to min
					}
					RBTransplant(delete, min);		// Transplant min and min's right subtree
					min.left = delete.left;		// Set min's left child to delete's left child
					min.left.parent = min;		// Set min's left childs parent to min
					min.color = delete.color;		// min's color is delete's color
			}
			if (minOriginalColor == Color.BLACK) {			// If the original color of min was Black, call fixup
					RBDeleteFixup(x);			// Call fixup on x
			}
			updateRanking();		// Update ranking of nodes
		}

		/**
		 * Fixes the RB Tree to ensure it maintains all RB Tree properties after deletion
		 * 
		 * @param Node
		 *              node
		 * @return none
		 */
		public void RBDeleteFixup(Node node) {
			Node w;			// Node placeholder
			while ((node != this.root) && (node.color == Color.BLACK)) {		// While the node is a child and its parent is red
					if (node == node.parent.left) {		// If node is a left child
						w = node.parent.right;			// Hold node's sibling (a right child)
						// CASE 1
						if (w.color == Color.RED) {		// If sibling is red
								w.color = Color.BLACK;			// Color it black
								node.parent.color = Color.RED;		// Set node's parent to red
								leftRotate(node.parent);			// Left rotate about node's parent
								w = node.parent.right;			// Set sibling to right child
						}
						// CASE 2
						if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {		// If sibling's children are both black
								w.color = Color.RED;		// Color sibling red
								node = node.parent;		// Set node to be its parent
						} else {			// Both children are not black
								// CASE 3
								if (w.right.color == Color.BLACK) {		// If the right child is black
									w.left.color = Color.BLACK;		// color left child black
									w.color = Color.RED;			// color sibling red
									rightRotate(w);			// Right rotate around sibling
									w = node.parent.right;			// Set sibling to node right child
								}
								// CASE 4
								w.color = node.parent.color;		// Set siblings color to node's parent color
								node.parent.color = Color.BLACK;		// Color node's parent black
								w.right.color = Color.BLACK;			// Color siblings right child black
								leftRotate(node.parent);			// Left rotate about node's parent
								node = root;				// Set node to be the root of tree
						}
					} else {			// If node is a right child
						w = node.parent.left;			// Store sibling (left child)
						// CASE 1
						if (w.color == Color.RED) {		// If sibling is red
								w.color = Color.BLACK;			// Color sibling black
								node.parent.color = Color.RED;		// Set parent to red
								rightRotate(node.parent);			// Right rotate around parent
								w = node.parent.left;			// Set sibling to be left child
						}
						// CASE 2
						if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {		// If both siblings children are black
								w.color = Color.RED;			// Color sibling red
								node = node.parent;			// Set node to be its parent
						} else {
								// CASE 3
								if (w.left.color == Color.BLACK) {		// If siblings left child is black
									w.right.color = Color.BLACK;		// color siblings right child to black
									w.color = Color.RED;				// color sibling to red
									leftRotate(w);			// Left rotate around sibling
									w = node.parent.left;			// Set sibling to be its parents left child
								}
								// CASE 4
								w.color = node.parent.color;		// Set siblings color to be parents color
								node.parent.color = Color.BLACK;		// color parents color to black
								w.left.color = Color.BLACK;		// Color siblings left child to black
								rightRotate(node.parent);		// Right rotate around parent
								node = root;		// Set node to root of tree
						}
					}
			}
			this.root.color = Color.BLACK;			// Set root of tree to Black (RB Tree property)
		}

		/**
		 * Gets size of a particular node's subtree
		 * 
		 * @param Node
		 *              subtree
		 * @return int size
		 */
		private int size(Node subtree) {
			if (subtree == null || subtree == nil) {		// If subtree is null, size is 0, return
					return 0;		// Size is 0
			}
			return 1 + size(subtree.left) + size(subtree.right);	// Return current node (size 1) + size of left subtree + size of right subtree
		}

		/**
		 * Gets size of entire RBT
		 * 
		 * @param none
		 * @return int size
		 */
		public int getSize() {
			return size(getRoot());		// Get size of root
		}

		/**
		 * Search for rankings 0 to size of tree and set ranking of Rank object
		 * 
		 * @param none
		 * @return none
		 */
		public void updateRanking() {
			int i = 0;
			int size = getSize();
			while (i < size) {
					Node searchi = search(i);		// Search for ranking of i
					if (searchi != nil && searchi != null) {
						searchi.key.setRanking(i + 1);		// Set ranking of Rank object to i
						i++;
					}
			}
		}

		/**
		 * Search for k'th largest node in tree
		 * 
		 * @param int
		 *              k
		 * @return Node
		 */
		public Node search(int k) {
			if (k < 0 || k >= getSize()) {		// If number want is less than 0 or greater than size of tree
					throw new IllegalArgumentException("argument to search() is invalid: " + k);		// Throw exception
			}
			Node x = select(root, k);		// Find the node at int k
			return x;		// Return the node found
		}

		/**
		 * Get k'th largest node in tree
		 * 
		 * @param Node
		 *              x, int k
		 * @return Node
		 */
		private Node select(Node x, int k) {
			if (x == null || x == nil)                       		// If x is null
					return null;		// return null
			int t = size(x.right);		// Get size of right subtree
			if (t > k)                       		// If right subtree size is greater than rank want to find
					return select(x.right, k);		// Look through right subtree
			else if (t < k)                       		// If right subtree size is less than rank want to find
					return select(x.left, k - t - 1);		// Look through left subtree
			else
					return x;		// Return node found at k
		}

}

class Node implements Comparable<Node> {
		Rank key;			// Node holds Rank object
		Node parent, left, right;		// Has parent, left, right pointers
		Color color;			// false is black, true is red

		/**
		 * Construct node with Rank object
		 * 
		 * @param Rank
		 *              obj
		 * @return none
		 */
		public Node(Rank obj) {
			this.key = obj;		// Set key to Rank object
			this.left = this.right = this.parent = RBTree.nil;		// Parent, left, right pointers are null
		}

		/**
		 * Construct node with Color object
		 * 
		 * @param Color
		 *              c
		 * @return none
		 */
		public Node(Color c) {
			this.left = this.right = this.parent = RBTree.nil;		// Parent, left, right pointers are null
			this.color = c;			// Set color to inputted value
		}

		/**
		 * Compare two nodes by comparing their Rank objects
		 * 
		 * @param Node
		 *              o
		 * @return int
		 */
		public int compareTo(Node o) {
			if (o == RBTree.nil) {			// If o is nil go right
					return 0;
			} else {
					return this.key.compareTo(o.key);		// Compare the two keys
			}
		}

}
