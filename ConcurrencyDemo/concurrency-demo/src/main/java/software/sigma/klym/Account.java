package software.sigma.klym;

public class Account {

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

    public void withdraw(int bal){
        balance = balance - bal;
    }

    public void deposit(int bal){
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
