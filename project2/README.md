# Two-Tier Client-Server Application Development With MySQL and JDBC

Summary: 
The purpose of this project is to create a java application that will allow any 
client to execute commands against the database. It is a Java GUI-base application front-end 
that will accept any MySQL commands, it will be passed through JDBC connection to the 
MySQL database server, execute the statement and return the result to the client 

DisplayQueryApp.java
* This class is what displays the Application, it is essentially the GUI
* I did a bit of experimenting with using buttons, panels, labels and add different actions to the buttons.
* The GUI illustrates a login portion on the top left panel. You can login either as the root or the client, if the user credentials matches with the root or client properties files then itll allow access 
* Top right panel is the command prompt for the user. They can enter SQL commands to be executed and it will present the result on the lower panel.
* The Client has limits commands (SELECT).

ResultSetTableModel.java
* This class is what gets the result from the query that has been submitted by the user and creates a table to be sent back to the GUI.
* There are two other things going on when a query has been submitted
  * after ever Update that the root user submits another connection gets created (as the root) and iterates a counter to keep track of how many updates has been made (this does not get sent to the GUI)
  * The same things happens when every a single query has been submitted
  * So there are two counters that are being handled...a query counter and a update counter.
  * This gets updated on a different server called operationslog
  * It uses the operationslog.properties to log in