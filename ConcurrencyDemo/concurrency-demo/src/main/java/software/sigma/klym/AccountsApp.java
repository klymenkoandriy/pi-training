package software.sigma.klym;

public class AccountsApp {

    private static int numAccouns = 10;
    private static int numTransactions = 100;
    
    public static void main(String args[]) {
        printHello();
        setParameters(args);
        printParameters();
        
        Bank bank = new Bank(numAccouns);
        randomTransfers(bank, numTransactions);
    }

    public static void printHello() {
        System.out.println("**********  Concurrency demo. ***********");
    }

    public static void setParameters(String args[]) {
        if (args.length == 2) {
            numAccouns = Integer.parseInt(args[0]);
            numTransactions = Integer.parseInt(args[1]);
        }
    }

    public static void printParameters() {
        System.out.println(" Accouns: " + numAccouns + ", Transactions: " + numTransactions);
        System.out.println("*****************************************\n");
    }
    
    public static void randomTransfers(Bank bank, int transfersNumber) {
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
