package software.sigma.klym.bank;

import software.sigma.klym.exceptions.DepositException;
import software.sigma.klym.exceptions.InsufficientException;
import software.sigma.klym.exceptions.WithdrawException;

public class Account {

    private static final int ERROR_CHANCE = 20;

    private final int accountId;

    private int balance= 100;

    Account(int id) {
        this.accountId = id;
    }

    Account(int id, int balance) {
        this.accountId = id;
        this.balance = balance;
    }

    public int getBalance(){
        return balance;
    }

    void withdraw(int amount) throws WithdrawException, InsufficientException{
        if (amount > balance) {
            throw new InsufficientException();
        }
        
        if ((int) (Math.random() * 100 / ERROR_CHANCE) == 0) {
            System.out.println(">>> throw exception");
            throw new WithdrawException();
        }

        balance = balance - amount;
    }

    void deposit(int amount) throws DepositException{
        if ((int) (Math.random() * 100 / ERROR_CHANCE) == 0) {
            System.out.println(">>> throw exception");
            throw new DepositException();
        }

        balance = balance + amount;
    }

    int getId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Account [id=" + accountId + ", bal=" + balance + "]";
    }

}
