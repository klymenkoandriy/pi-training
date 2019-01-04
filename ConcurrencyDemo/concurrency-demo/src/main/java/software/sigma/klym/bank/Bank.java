package software.sigma.klym.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import software.sigma.klym.exceptions.DepositException;
import software.sigma.klym.exceptions.InsufficientException;
import software.sigma.klym.exceptions.WithdrawException;

public class Bank {

    public static final int MAX_AMOUNT = 100;

    private static final String NO_ACCOUNT_FORMAT = "[%s] There is no Account with id = %s.\n";
    private static final String TRANSFERED_FORMAT = "[%s] Transfered %d. Result> sender: %s, receiver: %s.\n";
    private static final String INSUFFICIENT_FORMAT = "[%s] Insufficient balance on %s to transfer %d.\n";

    private static final String TRY_WITHDRAW_FORMAT = "[%s] Try withdraw %s from %s.\n";
    private static final String TRY_DEPOSIT_FORMAT = "[%s] Try deposit %d to %s.\n";
    private static final String TRY_ROLLBACK_FORMAT = "[%s] Try return %d to %s.\n";

    private static final String WITHDRAW_ERROR_FORMAT = "[%s] Error: fail on withdraw %s from %s.\n";
    private static final String DEPOSIT_ERROR_FORMAT = "[%s] Error: fail on deposit %s to %s.\n";

    private List<Account> accounts = new ArrayList<>();

    private Lock bankLock;
    private String threadName;

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

    public int getAccountsSize() {
        return accounts.size();
    }
    
    public int getTotal() {
        return accounts.stream().mapToInt(account -> account.getBalance()).sum();
    }

    public void transfer(int from, int to, int amount) {
        bankLock.lock();
        
        threadName = Thread.currentThread().getName();
        try {
            performTransfer(from, to, amount);
        } finally {
            bankLock.unlock();
        }
    }

    private void performTransfer(int from, int to, int amount) {
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

        transfer(fromAccount, toAccount, amount);

    }

    private void transfer(Account fromAccount, Account toAccount, int amount) {
        String threadName = Thread.currentThread().getName();

        if (!withdraw(fromAccount, amount)) {
            return;
        }

        if (!deposit(toAccount, amount)) {
            System.out.printf(TRY_ROLLBACK_FORMAT, threadName, amount, fromAccount);
            while(!withdraw(fromAccount, -amount));
            return;
        }

        System.out.printf(TRANSFERED_FORMAT, threadName, amount, fromAccount, toAccount);
    }

    private boolean withdraw(Account fromAccount, int amount) {
        try {
            System.out.printf(TRY_WITHDRAW_FORMAT, threadName, amount, fromAccount);
            fromAccount.withdraw(amount);
            return true;
        } catch (WithdrawException e) {
            System.out.printf(WITHDRAW_ERROR_FORMAT, threadName, amount, fromAccount);
            return false;
        } catch (InsufficientException e) {
            System.out.printf(INSUFFICIENT_FORMAT, threadName, fromAccount, amount);
            return false;
        } 
    }
    
    private boolean deposit(Account toAccount, int amount) {
        try {
            System.out.printf(TRY_DEPOSIT_FORMAT, threadName, amount, toAccount);
            toAccount.deposit(amount);
            return true;
        } catch (DepositException e) {
            System.out.printf(DEPOSIT_ERROR_FORMAT, threadName, amount, toAccount);
            return false;
        }
    }
 
}
