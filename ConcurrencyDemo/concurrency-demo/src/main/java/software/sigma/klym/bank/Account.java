package software.sigma.klym.bank;

import software.sigma.klym.exceptions.DepositException;
import software.sigma.klym.exceptions.WithdrawException;

public class Account {

    private static final int ERROR_CHANCE = 5;

    private final int accountId;

    private int balance= 100;

    public Account(int id) {
        this.accountId = id;
    }

    public Account(int id, int balance) {
        this.accountId = id;
        this.balance = balance;
    }

    public int getBalance(){
        return balance;
    }

    public void withdraw(int bal) throws WithdrawException{
        if ((int) (Math.random() * 100 / ERROR_CHANCE) == 0) {
            System.out.println(">>> throw exception");
            throw new WithdrawException();
        }

        balance = balance - bal;
    }

    public void deposit(int bal) throws DepositException{
        if ((int) (Math.random() * 100 / ERROR_CHANCE) == 0) {
            System.out.println(">>> throw exception");
            throw new DepositException();
        }

        balance = balance + bal;
    }

    public int getId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Account [id=" + accountId + ", bal=" + balance + "]";
    }

}
