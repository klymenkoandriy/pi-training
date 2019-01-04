package software.sigma.klym;

import software.sigma.klym.bank.Bank;
import software.sigma.klym.bank.Transaction;

public class AccountsApp {

    private static int numAccouns = 10;
    private static int numTransactions = 100;
    
    public static void main(String args[]) throws InterruptedException {
        printHello();
        setParameters(args);
        printParameters();
        
        Bank bank = new Bank(numAccouns);
        printTotalBalance(bank.getTotal());
        
        randomTransfers(bank, numTransactions);
        
        Thread.sleep(Transaction.MAX_TIME + 1000);
        printTotalBalance(bank.getTotal());
        printBy();
    }

    public static void printHello() {
        System.out.println("**********  Concurrency demo. ***********");
    }

    public static void printTotalBalance(int total) {
        System.out.println("*****************************************");
        System.out.println("Total balance:" + total);
        System.out.println("*****************************************");
    }

    public static void printBy() {
        System.out.println("===========  Complete. ===========");
    }

    public static void setParameters(String args[]) {
        if (args.length == 2) {
            numAccouns = Integer.parseInt(args[0]);
            numTransactions = Integer.parseInt(args[1]);
        }
    }

    public static void printParameters() {
        System.out.println(" Accouns: " + numAccouns + ", Transactions: " + numTransactions);
    }

    public static void randomTransfers(Bank bank, int transfersNumber) throws InterruptedException {
        for (int i = 1; i <= transfersNumber; i++) {
            int toAccount = 1;
            int fromAccount = 1;
            int amount = (int) (Math.random() * Bank.MAX_AMOUNT + 1);
            while (toAccount == fromAccount) {
                toAccount = (int) (Math.random() * bank.getAccountsSize() + 1);
                fromAccount = (int) (Math.random() * bank.getAccountsSize() + 1);
            } 

            Thread t = new Thread(new Transaction(bank, fromAccount, toAccount, amount));
            t.start();
        }

    }

}
