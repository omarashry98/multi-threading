package Part1;
import java.util.Random;

public class Withdrawal implements Runnable{

    private static final int MAX_WITHDRAWAL = 99;
    private static Random generator = new Random();
    private BankAccount sharedLocation;
    String tname;

    public Withdrawal(BankAccount shared, String name){
        sharedLocation = shared;
        tname = name;
    }

    @Override
    public void run() {

        while(true){
            try{
                int withdrawAmount = (generator.nextInt(MAX_WITHDRAWAL) + 1);
                sharedLocation.withdraw(withdrawAmount, tname);
                Thread.sleep((long)(Math.random() * 350));
            } catch (Exception e){
                System.out.println("Exception thrown withdrawing!");
            }
        }


    }
}
