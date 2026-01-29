import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.github.lalyos.jfiglet.FigletFont;

/**
 * Simple Bank Application
 * 
 * Features:
 * - Create bank accounts with account number, holder name, and initial balance
 * - View all accounts
 * - Search account by account number
 *   - Deposit
 *   - Withdraw
 *   - Delete account
 * 
 * This program is console-based and intended for students to study basic Java concepts.
 */
public class BankSimpleApp {

    public static void main(String[] args) throws IOException, InterruptedException {

        try {
            // ASCII Title using Figlet library
            String asciiTitle = FigletFont.convertOneLine("Simple Bank App");
            String greenOnRed = "\u001B[32m\u001B[41m"; // green text on red background
            String reset = "\u001B[0m";

            // Cancel flag used in input loops
            boolean cancelled = false;

            // Variables for creating account
            String accountNumber = null;
            String accountHolderName = null;
            double initialBalance = 0;

            System.out.println(greenOnRed + asciiTitle + reset);

            // List to store all bank accounts
            List<BankAccount> bankAccount = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);
            int mainChoice = 0;

            // Main application loop
            do {
                System.out.println("Bank Application Menu:");
                System.out.println("1. Create Account");
                System.out.println("2. View All Accounts");
                System.out.println("3. Search Account by Account Number");
                System.out.println("4. Exit Application");
                System.out.print("Enter your choice: ");

                // Handle non-integer inputs
                try {
                    mainChoice = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException ie) {
                    System.out.println("Enter NUMBER ONLY");
                    scanner.nextLine();
                    mainChoice = -1; // invalid choice
                }

                switch (mainChoice) {

                    // ================= CREATE ACCOUNT =================
                    case 1:
                        System.out.println("=====================================");

                        // Input: Account Number
                        while (true) {
                            System.out.print("Press 0 to cancel / Enter Account Number: ");
                            accountNumber = scanner.nextLine();
                            boolean ifAccountExist = false;

                            if (accountNumber.equals("0")) {
                                cancelled = true;
                                break;
                            }

                            if (!accountNumber.matches("\\d+")) {
                                System.out.println("Numbers only.");
                                continue;
                            }

                            if (accountNumber.length() > 6) {
                                System.out.println("Max 6 digits only.");
                                continue;
                            }

                            // Check if account number already exists
                            for (BankAccount acc : bankAccount) {
                                if (acc.getAccountNumber().equals(accountNumber)) {
                                    ifAccountExist = true;
                                    break;
                                }
                            }

                            if (ifAccountExist) {
                                System.out.println("Account Already Existed... Try another number.");
                                continue;
                            }

                            break;
                        }

                        if (cancelled) {
                            System.out.println("Account creation cancelled.");
                            cancelled = false;
                            break;
                        }

                        // Input: Account Holder Name
                        while (true) {
                            System.out.print("Press 0 to cancel / Enter Account Holder Name: ");
                            accountHolderName = scanner.nextLine();

                            if (accountHolderName.equals("0")) {
                                cancelled = true;
                                break;
                            }

                            if (accountHolderName.trim().isEmpty()) {
                                System.out.println("Name cannot be empty.");
                                continue;
                            }

                            if (accountHolderName.matches("\\d+")) {
                                System.out.println("Name cannot be numbers.");
                                continue;
                            }

                            break;
                        }

                        if (cancelled) {
                            System.out.println("Account creation cancelled.");
                            break;
                        }

                        // Input: Initial Balance
                        while (true) {
                            System.out.print("Press 0 to cancel / Enter Initial Balance: ");
                            String input = scanner.nextLine();

                            if (input.equals("0")) {
                                cancelled = true;
                                break;
                            }

                            try {
                                initialBalance = Double.parseDouble(input);

                                if (initialBalance < 0) {
                                    System.out.println("Balance cannot be negative.");
                                    continue;
                                }

                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Enter a valid number.");
                            }
                        }

                        if (cancelled) {
                            System.out.println("Account creation cancelled.");
                            cancelled = false;
                            break;
                        }

                        // Create account object and add to list
                        BankAccount bankDetails = new BankAccount(accountNumber, accountHolderName, initialBalance);
                        bankAccount.add(bankDetails);

                        System.out.println("Account successfully created!");
                        System.out.println("=====================================");
                        break;

                    // ================= VIEW ACCOUNTS =================
                    case 2:
                        System.out.println("=====================================");
                        if (bankAccount.isEmpty()) {
                            System.out.println("No account yet.");
                        } else {
                            int index = 1;
                            for (BankAccount bk : bankAccount) {
                                System.out.println("[" + index + "]");
                                System.out.println("Holder: " + bk.getAccountHolderName());
                                System.out.println("Number: " + bk.getAccountNumber());
                                System.out.println("Balance: " + bk.getInitialBalance());
                                System.out.println();
                                index++;
                            }
                        }
                        System.out.println("=====================================");
                        break;

                    // ================= SEARCH ACCOUNT =================
                    case 3:
                        System.out.println("=====================================");
                        String accNum;

                        if (bankAccount.isEmpty()) {
                            System.out.println("No account yet.");
                        } else {

                            while (true) {
                                System.out.print("Enter account number to search: ");
                                accNum = scanner.nextLine();

                                if (!accNum.matches("\\d+")) {
                                    System.out.println("Numbers only.");
                                    continue;
                                }

                                if (accNum.length() > 6) {
                                    System.out.println("Max 6 digits only.");
                                    continue;
                                }

                                // Find account
                                BankAccount foundAccount = null;
                                for (BankAccount acc : bankAccount) {
                                    if (accNum.equals(acc.getAccountNumber())) {
                                        foundAccount = acc;
                                        break;
                                    }
                                }

                                if (foundAccount != null) {
                                    System.out.println("-----------------------------------------------------------");
                                    System.out.println("Account Found: " + foundAccount.getAccountHolderName());
                                    System.out.println("Current Balance: " + foundAccount.getInitialBalance());

                                    // Account operations loop
                                    int accountChoice = 0;

                                    do {
                                        System.out.print("What would you like to do? "
                                                + "\n1. Deposit"
                                                + "\n2. Withdraw"
                                                + "\n3. Delete Account"
                                                + "\n4. Back to Main Menu\n"
                                                + "Enter your choice: ");

                                        try {
                                            accountChoice = scanner.nextInt();
                                            scanner.nextLine();
                                        } catch (InputMismatchException ie) {
                                            System.out.println("Enter NUMBER ONLY");
                                            scanner.nextLine();
                                            accountChoice = -1; // force default
                                        }

                                        // ===== DEPOSIT =====
                                        if (accountChoice == 1) {
                                            System.out.println("=====================================");
                                            while (true) {
                                                System.out.print("Enter amount to deposit (0 to cancel): ");
                                                String input = scanner.nextLine();

                                                if (input.equals("0")) break;

                                                try {
                                                    double depositAmount = Double.parseDouble(input);

                                                    if (depositAmount <= 0) {
                                                        System.out.println("Deposit must be greater than 0.");
                                                        continue;
                                                    }

                                                    double newBalance = foundAccount.getInitialBalance() + depositAmount;
                                                    foundAccount.setInitialBalance(newBalance);

                                                    System.out.println("Deposit successful!");
                                                    System.out.println("Available Balance: " + foundAccount.getInitialBalance());
                                                    break; // exit deposit loop after success

                                                } catch (NumberFormatException e) {
                                                    System.out.println("Enter a valid number.");
                                                }
                                            }
                                            System.out.println("=====================================");

                                        // ===== WITHDRAW =====
                                        } else if (accountChoice == 2) {
                                            System.out.println("=====================================");
                                            while (true) {
                                                System.out.print("Enter amount to withdraw (0 to cancel): ");
                                                String input = scanner.nextLine();

                                                if (input.equals("0")) break;

                                                try {
                                                    double withdrawAmount = Double.parseDouble(input);

                                                    if (withdrawAmount <= 0) {
                                                        System.out.println("Amount must be greater than 0.");
                                                        continue;
                                                    }

                                                    if (withdrawAmount > foundAccount.getInitialBalance()) {
                                                        System.out.println("Insufficient balance.");
                                                        continue;
                                                    }

                                                    double newBalance = foundAccount.getInitialBalance() - withdrawAmount;
                                                    foundAccount.setInitialBalance(newBalance);

                                                    System.out.println("Withdraw successful!");
                                                    System.out.println("Available Balance: " + foundAccount.getInitialBalance());
                                                    break; // exit withdraw loop after success

                                                } catch (NumberFormatException e) {
                                                    System.out.println("Enter a valid number.");
                                                }
                                            }
                                            System.out.println("=====================================");

                                        // ===== DELETE ACCOUNT =====
                                        } else if (accountChoice == 3) {
                                            System.out.println("=====================================");
                                            System.out.print("Are you sure you want to delete this account? (Y/N): ");
                                            String confirm = scanner.nextLine();

                                            if (confirm.equalsIgnoreCase("Y")) {
                                                Iterator<BankAccount> it = bankAccount.iterator();
                                                while (it.hasNext()) {
                                                    BankAccount acc = it.next();
                                                    if (acc == foundAccount) {
                                                        it.remove(); // safe delete
                                                        System.out.println("Account deleted successfully.");
                                                        break;
                                                    }
                                                }
                                                break; // exit account menu after deletion
                                            } else {
                                                System.out.println("Delete cancelled.");
                                            }
                                            System.out.println("=====================================");

                                        // ===== BACK TO MAIN MENU =====
                                        } else if (accountChoice == 4) {
                                            System.out.println("Back to main menu...");
                                            break;

                                        } else {
                                            System.out.println("=====================================");
                                            System.out.println("Invalid input");
                                            System.out.println("=====================================");
                                        }

                                    } while (accountChoice != 4);

                                } else {
                                    System.out.println("No account found with number: " + accNum);
                                    continue;
                                }
                                break; // exit search loop after account found or not
                            }

                        }
                        System.out.println("=====================================");
                        break;

                    // ================= EXIT APPLICATION =================
                    case 4:
                        System.out.println("Exiting application...");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

                System.out.println();

            } while (mainChoice != 4); // loop until user chooses to exit

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
