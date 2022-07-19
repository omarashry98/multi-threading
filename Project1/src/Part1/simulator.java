package Part1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class simulator {
    public static final int MAX_AGENTS = 15;

    public static void main(String[] args){
        ExecutorService application = Executors.newFixedThreadPool(MAX_AGENTS);
        BankAccount sharedLocation = new BankAccount();
        try{

            System.out.println("Deposit Agents\t\t\t\t\t Withdrawal Agents\t\t\t\t\tBalance");
            System.out.println("--------------\t\t\t\t\t -----------------\t\t\t\t\t------------------");

            for(int i = 0; i<5; i++){
                application.execute(new Depositor(sharedLocation, "DT" + i));
            }
            for(int i = 0; i<10; i++){
                application.execute(new Withdrawal(sharedLocation, "WT" + i));
            }




        } catch(Exception e){
            e.printStackTrace();
        }
        application.shutdown();
    }
}
