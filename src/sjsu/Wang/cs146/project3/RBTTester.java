package sjsu.Wang.cs146.project3;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

public class RBTTester {
	@Test
	// Test the Red Black Tree
	public void test() {
		RedBlackTree<String> rbt = new RedBlackTree<>();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
		assertEquals("DBACFEHGIJ", makeString(rbt));
		String str = "Color: 1, Key:D Parent: \n" + "Color: 1, Key:B Parent: D\n" + "Color: 1, Key:A Parent: B\n"
				+ "Color: 1, Key:C Parent: B\n" + "Color: 1, Key:F Parent: D\n" + "Color: 1, Key:E Parent: F\n"
				+ "Color: 0, Key:H Parent: F\n" + "Color: 1, Key:G Parent: H\n" + "Color: 1, Key:I Parent: H\n"
				+ "Color: 0, Key:J Parent: I\n";
		System.out.println(makeStringDetails(rbt));
		assertEquals(str, makeStringDetails(rbt));
	}

	/**
	 * Tests the loading of the dictionary into a the Red Black Tree with correct properties as well as 
	 * the time it takes. Along with this is takes in a poem to look up words in the dictionary and increments a counter
	 * whenever it finds a word in the dictionary correctly. This too also returns a time for how long it takes to find the words.
	 * @throws Exception
	 */
	@Test
	public void testLoading() throws Exception {
		BufferedReader input = new BufferedReader(new FileReader("dictionary.txt"));
		RedBlackTree<String> dictionary = new RedBlackTree<>();
		long time = System.currentTimeMillis();
		while (input.readLine() != null) {
			dictionary.insert(input.readLine());
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Loading words: " + time + " ms");
		
		//loads poem
		BufferedReader poem = new BufferedReader(new FileReader("Poem.txt"));
		int wordsFound = 0;
		
		time = System.currentTimeMillis();
		while(poem.readLine() != null) {
			String s = poem.readLine();
			if(s == null) {
				break;
			}
			String[] arr = s.toLowerCase().replaceAll( "[^a-zA-Z\\s]", "" ).split(" ");
			for(String string : arr) {
				if(dictionary.lookup(string)) {
					wordsFound++;
				}
			}
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Finding words: " + time + " ms");
		System.out.println("Words found: " + wordsFound);
		poem.close();
		input.close();
	}

	/**
	 * Tests the right rotation of the tree using only four strings
	 */
	@Test
	public void testRotateTreeRight() {
		String[] words = { "n", "p", "z", "b" };
		RedBlackTree<String> tree = new RedBlackTree<String>();

		// Building tree
		for (String word : words) {
			tree.insert(word);
		}

		// Rotating tree about its root
		tree.rotateRight(tree.getRoot());

		// Build actual output
		StringBuilder actual = new StringBuilder();

		tree.preOrderVisit(new RedBlackTree.Visitor<String>() {
			public void visit(RedBlackTree.Node<String> n) {
				actual.append(n.key + " ");
			}
		});

		// Expected output
		String expected = "b n p z ";
		assertEquals(expected, actual.toString());
	}

	/**
	 * Tests the left rotation of the tree using only four strings
	 */
	@Test
	public void testRotateTreeLeft() {
		String[] wording = { "b", "a", "d", "c" };
		RedBlackTree<String> treeTest = new RedBlackTree<String>();

		// Building tree
		for (String word : wording) {
			treeTest.insert(word);
		}

		// Rotating tree about its root
		treeTest.rotateLeft(treeTest.getRoot());

		// Build actual output
		StringBuilder actual = new StringBuilder();

		treeTest.preOrderVisit(new RedBlackTree.Visitor<String>() {
			public void visit(RedBlackTree.Node<String> n) {
				actual.append(n.key + " ");
			}
		});

		// Expected output
		String expected = "d b a c ";

		assertEquals(expected, actual.toString());
	}

	// add tester for spell checker

	public static String makeString(RedBlackTree t) {
		class MyVisitor implements RedBlackTree.Visitor {
			String result = "";

			public void visit(RedBlackTree.Node n) {
				result = result + n.key;
			}
		}
		;
		MyVisitor v = new MyVisitor();
		t.preOrderVisit(v);
		return v.result;
	}

	public static String makeStringDetails(RedBlackTree t) {
		{
			class MyVisitor implements RedBlackTree.Visitor {
				String result = "";

				public void visit(RedBlackTree.Node n) {
					if (n.parent == null) {
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: " + "\n";
					} else {
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: " + n.parent.key + "\n";
					}
				}

			}
			;
			MyVisitor v = new MyVisitor();
			t.preOrderVisit(v);
			return v.result;
		}
	}
}