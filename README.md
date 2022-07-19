# An Application Employing Synchronized/Cooperating Multiple Threads In Java Using Locks – A Banking Simulator

Summary: The purpose of this project is to practice programming cooperating, synchronized multiple threads of
execution. This Project will be simulating a Bank system by creating synchronized threads that require cooperation 
and communication amongst the various agents. The main functions that will be used is mutual exclusion and mutexes.

Simulator file
* This class initializes all our agents. We have 5 depositing agents and 10 withdraws agents
* The reason we have 5 depositing and 10 withdrawal agents is to simulate realism in a banking system
  * you are usually withdrawing in a banking system rather than depositing 
  * to counteract the withdrawals having more agents than the depositing agents we increase the amount of money being deposited 

Withdrawal file
* The withdrawal class simply generates a random number between 0-99 adding one just in case we get 0.
* Then we make the thread sleep for a period of time, sleep time is going to be smaller than the depositor since we have double about of agents.

Depositor file
* The Depositor class generates a number between 0-500, this number is exponentially higher than the withdrawal agents because they are occurring much frequently.
* Then we sleep the depositor thread for a longer period of time.

BankAccount file
* This class is where we use the mutexes and deals with exceptions along with side cases 
* deposit class
  * if the deposit class gets called we first lock the threading so no other agents can access the bank account until the deposit is complete
  * The reason we need to lock is that if multiple agents are depositing and withdrawing at the same time for example it reads the withdrawal of $50 at the same time and say we only have $50 in our account, the account would go negative $50.
  * We have a case for flagged transaction, this is here just for realism. If a deposit is more than 350 we flag that transaction, and it gets logged.
* withdrawal class
  * Same thing as the deposit class except we are checking for insufficient funds
  * We also flag a transaction if its over $75.
* flaggedTransaction class
  * This simply checks if the transaction is a deposit or withdrawal then prints a message that corresponds to it.
