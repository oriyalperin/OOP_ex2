# ex2_OOP

EX2 assignment

In this task we were required to construct the properties of a weighted directed graph, using the two classes that were required to implement which are - DWgraph_DS, DWGraph_Algo.

Before we start with one of them, we had to implement the node_data class and the edge_data class in which we inserted all the previous was and end properties.



First, we will explain the implementation of the DWgraph_DS class.
In this lesson we were asked to understand the features of a weighted directed graph.

We will first explain the general idea behind the construction.
The data structure that will reflect events A weighted directed graph is 2 HashMap, the first is nodes and is designed to store all the vertices of the graph that it will actually contain an entire key and node_data.

We will move to the second HashMap called Edges which stores the integral which is the key event and within it another HashMap which stores the number and pay_Edge_Data, an event which has the first key connected to the second key and their side is the side that is on the edges.

During the application we built functions of receiving the endpoint, adding a vertex, connecting 2 vertices, a collection of all the previous ones, a collection that brings the whole end of each vertex we have defined its key.
In addition to deleting a vertex, deleting the edge. In addition to this we have the variables of the class which are ModeCount which basically summed the amount of operations in the graph and edgeCount which counted the number of sides in the graph.


We will move to the DWGraph_Algo class application where we have graphed as an object and applied 6 functions, and we will expand on which functions to define.
The copy function opens a new graph into which we will put all the vertices in the new graph and then go over each vertex on which we will pass all its neighbors to which it is connected, and make a rib between them using the connect function and thus we created the graph we got in a new graph.

We will move to the isConnected function where we will check the graph to see if it is strongly connected.
In this function we used the Tarjan algorithm where we could actually find out how the graph is structured and how many binding elements we have.
The data structure we will use is a link list with which we can know how many binding extensions we have and thus know if the graph is connected and also with the help of the stack that helps us continue to check on each vertex its neighbors and thus the algorithm process is built to check whether we can get back to the same side.
Using this we will discover how many connected components we have in the graph.

We will move to the shortestPathDist function where we want to discover the shortest path in the graph from vertex to vertex.
The data structure we will use is HashMap where we will actually progress to the neighbors of the same vertex each time to find the lowest weight, so each time we push the vertex into the Hashmap we will delete it and then run on the ribs it connects to, and find out which weight is lowest Ours and thus we will discover the shortest weight in the graph.
And at the end of the operations we will return the trajectory we will clear the graph the next time we want to use it.

We will make the same moves on the shortestPath function where we want to bring the shortest path. And uses the ArrayList data structure that it accepts node_data objects and works on a very similar principle, with in addition the ArrayList that keeps the path itself in the list.

We will move to the save function where we want to save our graph within a JSON file.
What we do is create a new file and a new JSON file and put into it the graph we have.
 
We will move to the load function where we had to read from a JSON file where we will read from a JSON file.
To do this we have made a class on all the objects that are in use and that we can work with and configure them with JSON files.

המחלקה json_to_graph
דואגת שההמרה מג'ייסון לגרף DWGraph_DS תתבצע כראוי 

המחלקה json_to_graphGame
 דוגאת שהמרה של גרף של המשחק מקובץ ג'ייסון לגרף מסוג directed_weighted_graph תתבצע כראוי

המחלקה connected
שומרת נתונים של קודקודים בגרף שמבצעים עליו את הפוקנציה isConnected

המחלקה CL_Pokemon
מייצגת כל פוקימון ע"י מיקום, צלע עליה הפוקימון נמצא, מה הערך של הפוקימון..
השימוש העיקרי בפוקימון יהיה במיקום שלו ע"מ למצוא את הסוכן הקרוב אליו כדי שאותו סוכן יוכל "לאכול" אותו.

המחלקה CL_Agent
מייצגת סוכן ע"י מיקום, כמה נקודות הרוויח עד כה, מהירותו..
השימוש העיקרי בסוכן יהיה במיקום ובמהירות שלו ע"מ לחשב את המרחקים ממנו לפוקימונים אחרים וכך למצוא את הפוקימון האידאלי שאותו כדאי לו לאכול ע"מ להרוויח כמה שיותר נקודות בכמה שפחות זמן.

המחלקה Arena
תפקידה העיקרי הוא לטפל בפלט שאנחנו מקבלים מהפונקציות של המשחק מהשרת, את רובם לתרגם ממחרוזות ג'ייסון לאובייקטים ממשים (כמו רשימת הפוקימונים, רשימת הסוכנים, הגרף עצמו)
בנוסף מעדכנת עבור כל הפוקימונים את הצלע הנוכחית עליה הם נמצאים.
המחלקה MyFrame
תפקידה לתרגם את כל האלמנטים של המשחק: גרף= קודקודים וצלעות, סוכנים ופוקימונים- לכדי משחק ויזואלי עם אלמנטים ממשיים. בנוסף דואגת לעדכן מדי זמן מוגדר את השינויים על החלון הגרפי.
המחלקה Ex2
תפקידה להריץ את המשחק מכל הבחינות: וויזאולית ולוגית. התחברות למשחק והרצת שלב, ושימוש באלגוריתם לתיאום אידיאלי בין סוכן לפוקימוןץ
רעיון האלגוריתם בכל מוב:
לבדוק את המרחקים של המסלולים הקצרים ביותר בין כל סוכן לכל פוקימון(בהתחשב במהירות של כל סוכן) ולהתחיל "לשדך" בין הסוכן והפוקימון שהמרחק בינם היה הכי קצר מכל הזוגות הסדורים. עבור כל סוכן יישמר המסלול האידאלי שלו אל פוקימון היעד. לאחר שכל הסוכנים שודכו, נותר לקדם אותם אל עבר הקודקוד הבא במסלול שלהם. 
בכל מוב תתבצע הבדיקה של המרחקים בין כל פוקימון לבין כל סוכן ע"מ להבטיח שאם סוכן אחד התפנה ונודע לנו שהוא קרוב יותר אל הפוקימון שסוכן אחר בדרכו אליו- נרצה שהסוכן שהתפנה ילך אליו והסוכן שהיה בדרכו- ימצא פוקימון אחר ללכת לעברו. כלומר אין בלעדיות של סוכן על פוקימון עד שהסוכן יגיע אליו, אלא הבלעדיות היא רק עבור מוב אחד.

