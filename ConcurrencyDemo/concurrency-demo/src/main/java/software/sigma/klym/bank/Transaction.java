package software.sigma.klym.bank;

public class Transaction implements Runnable{

    private Bank bank;
    private int fromAccount;
    private int toAccount;
    private int amount;
 
    public Transaction(Bank bank, int fromAccount, int toAccount, int amount) {
        this.bank = bank;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void run() {
        bank.transfer(fromAccount, toAccount, amount);
 
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
