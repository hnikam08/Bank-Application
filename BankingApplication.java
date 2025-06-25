import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Account {
    private String accountNumber;
    private String customerName;
    private double balance;

    public Account(String accountNumber, String customerName, double balance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class TransactionHistory {
    private HashMap<String, ArrayList<String>> transactionHistory;

    public TransactionHistory() {
        transactionHistory = new HashMap<>();
    }

    public void addTransaction(String accountNumber, String transaction) {
        if (!transactionHistory.containsKey(accountNumber)) {
            transactionHistory.put(accountNumber, new ArrayList<>());
        }
        transactionHistory.get(accountNumber).add(transaction);
    }

    public ArrayList<String> getTransactionHistory(String accountNumber) {
        return transactionHistory.get(accountNumber);
    }
}

public class BankingApplication {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static TransactionHistory transactionHistory = new TransactionHistory();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            choice = getIntInput("Enter your choice: ");
            performOperation(choice);
        } while (choice != 6);
    }

    private static void displayMenu() {
        System.out.println("\nBanking Application Menu:");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Balance Inquiry");
        System.out.println("5. Transaction History");
        System.out.println("6. Exit");
    }

    private static void performOperation(int choice) {
        switch (choice) {
            case 1:
                createAccount();
                break;
            case 2:
                deposit();
                break;
            case 3:
                withdraw();
                break;
            case 4:
                balanceInquiry();
                break;
            case 5:
                transactionHistory();
                break;
            case 6:
                System.out.println("Exiting Banking Application...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void createAccount() {
        String accountNumber = getStringInput("Enter account number: ");
        String customerName = getStringInput("Enter customer name: ");
        double initialDeposit = getDoubleInput("Enter initial deposit: ");
        Account newAccount = new Account(accountNumber, customerName, initialDeposit);
        accounts.add(newAccount);
        System.out.println("Account created successfully!");
    }

    private static void deposit() {
        String accountNumber = getStringInput("Enter account number: ");
        double amount = getDoubleInput("Enter deposit amount: ");
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            transactionHistory.addTransaction(accountNumber, "Deposit: $" + amount);
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw() {
        String accountNumber = getStringInput("Enter account number: ");
        double amount = getDoubleInput("Enter withdrawal amount: ");
        Account account = findAccount(accountNumber);
        if (account != null) {
            if (account.withdraw(amount)) {
                transactionHistory.addTransaction(accountNumber, "Withdrawal: $" + amount);
                System.out.println("Withdrawal successful!");
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void balanceInquiry() {
        String accountNumber = getStringInput("Enter account number: ");
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Account Balance: $" + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transactionHistory() {
        String accountNumber = getStringInput("Enter account number: ");
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Transaction History for Account " + accountNumber + ":");
            ArrayList<String> transactions = transactionHistory.getTransactionHistory(accountNumber);
            if (transactions != null) {
                for (String transaction : transactions) {
                    System.out.println(transaction);
                }
            } else {
                System.out.println("No transactions found for this account.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine());
    }
}