package software.sigma.klym.bank;

public class Transaction implements Runnable{
    
    public static final int MIN_TIME = 300;
    public static final int MAX_TIME = 3000;

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
        try {
            Thread.sleep(MIN_TIME + (int)(Math.random() * (MAX_TIME-MIN_TIME)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bank.transfer(fromAccount, toAccount, amount);
    }

}
