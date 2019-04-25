**READ ME INSTRUCTION**

**In order to load in the text files, they must be placed within the java project and not
the package itself.**

There are 4 tests in the RBTTester.java file which are:

1. test() - 
		the JUnit test given to us through canvas
2. testLoad() - 
		this test loads the dictionary.txt file into a buffered reader and 
		proceeds to insert them into the RedBlackTree called dictionary. This should
		take a few minutes and it will report a timing for how long it took.
		Along with this it loads in a Robert Frost poem that calls the lookup
		method to see whether or not the words in the poem exist and increments a 
		counter which reports how many words in the poem were found within the dictionary.
		This too reports the timing for how long it took to look up the words in the tree.
3. testRotateTreeLeft() - 
		This takes 4 strings and checks to see if the tree restructures them correctly 
4. testRotateTreeRight() - 
		This also takes 4 strings and checks to see if the tree restructures them
		in the right orientation