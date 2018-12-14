import java.util.*;

// Author: Rabia Mohiuddin
public class BuildApp {
		ArrayList<Rank> URLObjects = new ArrayList<Rank>();			// ArrayList that stores search results
		RBTree urlRBT = new RBTree();				// BST for the manipulate search results

		/**
		 * private search method takes search string as input and returns ArrayList of WebPageURL objects
		 * 
		 * @param search
		 *              String -> search term
		 * @return ArrayList<Rank> -> ArrayList of type WebPageURL (implementation of Rank)
		 */
		private ArrayList<Rank> search(String search) {
			FunnyCrawler obj = new FunnyCrawler();			// Create web crawler object
			ArrayList<String> result = obj.getDataFromGoogle(search);		// Set of unique URLs from search results
			result.remove("");		// Remove any empty URLs or strings

			ArrayList<Rank> URLobjects = new ArrayList<Rank>();		// Create ArrayList of WebPageURL objects
			URLobjects.clear();		// Clear the local ArrayList to make sure nothing is inside
			int index = 1;
			for (String temp : result) {			// Iterate through unique URL results, create WebPageURL objects and add to ArrayList
					URLobjects.add(new WebPageURL(temp, index));			// Create WebPageURL object and add to URLobjects ArrayList
					index++;					// Increment index for next for loop iteration
			}

			return URLobjects;		// Return ArrayList of URLobjects
		}

		/**
		 * public search method that retrieves search string from user, calls search and retrieves results, then builds BST, and stores the results if first
		 * time being searched
		 * 
		 * @param searchTerm
		 *              String -> search term
		 * @return none
		 */
		public void enterSearch(String searchTerm) {
			URLObjects.clear();			// Clear the public URLObjects ArrayList to ensure no old results left behind

			URLObjects = search(searchTerm);		// Get new search results and store them in public ArrayList

			System.out.println("Your term  '" + searchTerm + "' found " + URLObjects.size() + " results");		// Print # of results to user

			RBTree urls = new RBTree();				// BST for the manipulate search results
			for (Rank urlobj : URLObjects) {			// For every Rank (url) object in the ArrayList, insert it into RBT by creating new node
					urls.RBTreeInsert(new Node(urlobj));			// Create new node with Rank object, insert in RBT
			}
			urlRBT = urls;

			printHeader();				// Print header columns
			urlRBT.inorderRBTreeWalk(urlRBT.getRoot());		// Call BST inorder walk on root of tree
			System.out.println();
		}

		/**
		 * Prints column headers or URL results: Rank | Total Score | Index | URL
		 * 
		 * @param none
		 * @return none
		 */
		public void printHeader() {
			System.out.println();			// Empty line for clarity
			System.out.format("\n%-7s%-15s%-15s%-25s%-15s\n", "Rank", "Total Score", "Index", "Color", "URL");		// Print header given space
		}

		/**
		 * Creates user interface that displays options to user, reads in their choice and calls respective functions and loops back to options
		 * 
		 * @param none
		 * @return none
		 */
		public void userInterface() {
			Scanner reader = new Scanner(System.in);  // Reading from System.in

			System.out.print("Enter a search term: ");			// Ask user for a search term to start application
			enterSearch(reader.nextLine());			// Read the search term in and call search function

			String option = null;			// Set option to null so can enter while loop
			while (!"0".equals(option)) {		// Option 0 represents Quit - As long as not quit, loop through options
					// Ask user what they want to do - display 9 options
					System.out.println("What would you like to do next?");
					System.out.println("1. View results from Red-Black Tree Inorder Walk");
					System.out.println("2. Search for a specific page rank");
					System.out.println("3. Insert a URL (based on Total Score) into search results (RBT)");
					System.out.println("4. Delete a URL (based on Page Rank) from search results (RBT)");
					System.out.println("5. Run another search");
					System.out.println("0. Quit");

					System.out.print("\nOption: ");		// Ask user to select one of the above options

					option = reader.nextLine().trim();		// Read in their option choice

					if (!"0".equals(option)) {		// If option is not quit, go through switch statement for respective options
						switch (option) {
						case "1":			// View search results from RBT inorder walk
								printHeader();				// Print header columns
								urlRBT.inorderRBTreeWalk(urlRBT.getRoot());		// Call BST inorder walk on root of tree
								System.out.println();
								break;

						case "2":		// Search for a specific page rank
								System.out.println("Which Rank item would you like to display?");
								int size = urlRBT.getSize();			// Get size (number of nodes) of current RBT
								System.out.println("There are currently " + size + "URLs");			// Print size of RBT to the user
								System.out.print("\nSearch URL Rank: ");		// Ask user to select a URL rank
								try {
									int rankNode = Integer.parseInt(reader.nextLine());			// Read in user rank choice
									if (rankNode > size || rankNode < 0) {				// Make sure choice is within the RBT size
											throw new InputMismatchException("Out of range");		// If not in RBT size, throw exception
									}
									Node rankItem = urlRBT.search(rankNode - 1);			// Retrieve the node returned from searching for rank
									printHeader();			// Print header columns and print object attributes with given format
									System.out.format("%-15s%-20s%-20s%-20s%-15s\n", rankItem.key.getRanking(), rankItem.key.getTotalScore(), rankItem.key.getIndex(), rankItem.color,
												rankItem.key.getName());

								} catch (InputMismatchException e) {			// Exception thrown if not in range or not integer
									System.out.println(e.getMessage());			// Print message to the console
								} catch (NumberFormatException e) {			// If not integer, catch exception
									System.out.println("Invalid rank - " + e.getMessage());			// Print message to the console
								}
								break;

						case "3":		// Insert a URL (based on Total Score) into search results (RBT)
								System.out.println("Enter a URL that starts with http:// or https://");
								System.out.print("URL: ");
								String insertURL = reader.nextLine().trim();			// Read in user's URL
								WebPageURL insertLink = new WebPageURL(insertURL, urlRBT.getSize());		// Create WebPageURL and set index
								System.out.print("Total Score of URL: ");
								try {
									int insertURLScore = Integer.parseInt(reader.nextLine());		// Read in total score of URL
									insertLink.setTotalScore(insertURLScore);		// Set WebPageURL object's total score
									urlRBT.RBTreeInsert(new Node(insertLink));		// Create Node with WebPageURL object and insert into URL BST
								} catch (InputMismatchException e) {			// Exception thrown if not integer
									System.out.println(e.getMessage());			// Print message to the console
								} catch (NumberFormatException e) {			// If user doesnt enter integer, catch exception
									System.out.println("Invalid score - " + e.getMessage());			// Print message to the console
								}

								break;

						case "4":		// Delete a URL (based on Page Rank) from search results (RBT)
								System.out.println("Which Rank item would you like to delete?");
								int sizeTree = urlRBT.getSize();			// Get size (number of nodes) of current BST
								System.out.println("There are currently " + sizeTree + " URLs");			// Print size of BST to the user
								System.out.print("\nDelete URL Rank: ");		// Ask user to select a URL rank
								try {
									int rankNode = Integer.parseInt(reader.nextLine());			// Read in user rank choice
									if (rankNode > sizeTree || rankNode < 0) {				// Make sure choice is within the BST size
											throw new InputMismatchException("Out of range");		// If not in BST size, throw exception
									}
									Node delete = urlRBT.search(rankNode - 1);			// Retrieve the node returned from searching for rank
									urlRBT.RBTreeDelete(delete);
									System.out.println("Rank item " + rankNode + " was deleted");
								} catch (InputMismatchException e) {			// Exception thrown if not in range or not integer
									System.out.println(e.getMessage());			// Print message to the console
								} catch (NumberFormatException e) {
									System.out.println("Invalid rank - " + e.getMessage());			// Print message to the console
								}
								break;

						case "5":		// Run another search
								System.out.print("Enter a search term: ");		// Ask for new search term
								enterSearch(reader.nextLine());			// Call search function with the search term
								break;

						default:		// If not options 0-5, ask user to reselect an option
								System.out.println("Please select one of the above options. Input must be a digit between 0 and 5\n");
						}
						System.out.println();
					} else {		// User has selected 0 meaning quit, say Goodbye
						System.out.println("Goodbye!");
					}

			}

			reader.close();		// Close scanner object

		}

		/**
		 * Welcomes user and calls the userInterface method
		 * 
		 * @param none
		 * @return none
		 */
		public static void main(String[] args) {
			BuildApp app = new BuildApp();			// Create BuildApp object to call userInterface
			System.out.println("Welcome to my mock Google search simulator using Red-Black Trees!");
			System.out.println("Author: Rabia Mohiuddin\n");
			app.userInterface();		// Call userInterface to start application
		}
}
