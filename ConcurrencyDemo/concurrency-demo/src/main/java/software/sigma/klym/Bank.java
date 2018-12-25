package software.sigma.klym;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Bank {

    public static final int MAX_AMOUNT = 100;

    private static final String NO_ACCOUNT_FORMAT = "[%s] There is no Account with id = %s.\n";
    private static final String TRANSFER_FORMAT = "[%s] Transfered %d. Result> sender: %s, receiver: %s.\n";
    private static final String INSUFFICIENT_FORMAT = "[%s] Insufficient balance on %s to transfer %d .\n";
    
    private List<Account> accounts = new ArrayList<>();

    private Lock bankLock;

    public Bank(final int accountNumber) {
        IntStream.rangeClosed(1, accountNumber)
            .forEach(i -> accounts.add(new Account(i, MAX_AMOUNT)));
        
        bankLock = new ReentrantLock();
    }

    public Account getById(int accountId) {
        return accounts.stream()
                .filter(account -> account.getId() ==  accountId)
                .findAny()
                .orElse(null);
    }
    
    public void transfer(int from, int to, int amount) {
        bankLock.lock();
        try {
            performTransfer(from, to, amount);
        } finally {
            bankLock.unlock();
        }
    }
    
    
    private void performTransfer(int from, int to, int amount) {
        String threadName = Thread.currentThread().getName();

        Account fromAccount = getById(from);
        Account toAccount = getById(to);

        if (fromAccount == null) {
            System.out.printf(NO_ACCOUNT_FORMAT, threadName, from);
            return;
        }

        if (toAccount == null) {
            System.out.printf(NO_ACCOUNT_FORMAT, threadName, to);
            return;
        }

        if (amount <= fromAccount.getBalance()) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            System.out.printf(TRANSFER_FORMAT, threadName, amount, fromAccount, toAccount);
        } else {
            System.out.printf(INSUFFICIENT_FORMAT, threadName, fromAccount, amount);
        }
    }
    
    public int getAccountsSize() {
        return accounts.size();
    }
    
}
