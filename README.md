# MockGoogleSearch using Red Black Trees
Mock Google Search Simulator using Red Black Trees and web crawlers

<b>How to Run Application</b>
1. Unzip application
2. Open a terminal or console window
3. Change directories to the bin folder of the unzipped project
4. Execute java -jar BuildApp.jar
5. Try all options to view complete functionality of application

<b>Implementation Overview</b>

The Google Search Engine Simulator allows a user to conduct a search of a term they
desire with five main sub functions. Once the search is submitted, a list of about 30 results are
displayed to the user along with options on what to do next. These options include:
1. View results from Red Black Tree Inorder Walk
2. Search for a specific page rank
3. Insert a URL (based on Total Score) into search results (RBT)
4. Delete a URL (based on Page Rank) from search results (RBT)
5. Run another search

The Simulator consists of six java classes and one interface: RBTree, Node,
FunnyCrawler, BuildApp, WebPageURL, and Rank Interface.

The BuildApp class uses all classes (RBTree, Node, FunnyCrawler, BuildApp, and
WebPageURL).

RBTree Node uses Rank Interface for its rank object and Color Enum to hold its color
instance.
