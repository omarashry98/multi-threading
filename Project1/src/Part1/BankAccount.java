package Part1;
import java.sql.Time;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.sql.Timestamp;
import java.io.*;

public class BankAccount implements TheBank{

    public static int balance = 0;
    private final Lock accesslock = new ReentrantLock();


    @Override
    public void deposit(int value, String name) {
        accesslock.lock();
        System.out.print("\nAgent " + name + " deposits $" + value);
        try{
            balance += value;
            System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t(+) $" + balance + "\n");
            if(value > 350)
                flaggedTransaction(value, name, "D");
        } catch(Exception e){
            System.out.println("Exception thrown depositing");
        }
        finally {
            accesslock.unlock();
        }
    }

    @Override
    public void withdraw(int value, String name) {
        accesslock.lock();
        System.out.print("\n\t\t\t\t\t\t\t\tAgent " + name + " withdraws $" + value);
        try{
            if((balance - value) < 0) {
                System.out.println("\t\t\t\tINSUFFICIENT FUNDS");
            }else {
                balance -= value;
                System.out.print("\t\t\t\t(-) $"+balance+"\n");
                if(value > 75)
                    flaggedTransaction(value, name, "W");
            }
        }catch(Exception e){
            System.out.println("Exception thrown depositing");
        }
        finally {
            accesslock.unlock();
        }
    }

    @Override
    public void flaggedTransaction(int value, String name, String type) throws IOException {
        StringBuilder str = new StringBuilder();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        FileWriter myWriter = new FileWriter("transactions.txt", true);
        try {
            if (type.equals("W")) {
                System.out.println("\n* * * Flagged transaction - Withdrawal Agent " + name + " made a withdrawal that exceeds $75.00 USD - see Flagged transaction log");
                str.append("\tWithdrawal Agent " + name + " issued a withdrawal of $" + value + " at: " + time + " EST\n");
                //myWriter.write("Withdrawal Agent " + name + " issued a withdrawal of $" + value + " at: " + time + " EST");
            } else {
                System.out.println("\n* * * Flagged transaction - Depositing Agent " + name + " made a deposit that exceeds $350.00 USD - see Flagged transaction log");
                str.append("Depositing Agent " + name + " issued a deposit of $" + value + " at: " + time + " EST\n");
                //myWriter.write("Depositing Agent " + name + " issued a deposit of $" + value + " at: " + time + " EST");
            }
            myWriter.write(str +"\n");
        } catch (Exception e) {
            System.out.println("\nError: problem writing to transaction file.\n");
        } finally {
            myWriter.close();
        }

    }
}
