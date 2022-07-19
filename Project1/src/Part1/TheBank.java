package Part1;

/*
     Name:Omar Ashry
     Course: CNT 4714 Summer 2022
     Assignment title: Project 1 – Synchronized, Cooperating Threads Under Locking
     Due Date: June 5, 2022
*/

import java.io.IOException;

public interface TheBank {
    public abstract void deposit(int value, String name);
    public abstract void withdraw(int value, String name);
    public abstract void flaggedTransaction(int value, String name, String type) throws IOException;
}
