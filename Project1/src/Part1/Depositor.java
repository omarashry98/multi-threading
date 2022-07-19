package Part1;
import java.util.Random;

import static java.lang.Math.random;
import static java.lang.Thread.sleep;

public class Depositor implements Runnable{

    private static final int MAX_DEPOSIT = 500;
    private static Random generator = new Random();
    private BankAccount sharedLocation;
    String tname;
    public static int money = 0;

    public Depositor(BankAccount shared, String name){
        sharedLocation = shared;
        tname = name;
    }

    @Override
    public void run() {

        while(true){
            try{
                int depositAmount = (generator.nextInt(MAX_DEPOSIT) + 1);
                Thread.sleep((long)(Math.random() * 900));
                sharedLocation.deposit(depositAmount, tname);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
