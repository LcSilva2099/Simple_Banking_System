package banking;

import java.sql.*;
import java.util.Scanner;

public class TextUI {

    private Scanner scanner;
    private CardMaker cardMaker;
    private CardDao database;

    public TextUI(Scanner scanner, CardDao database) {
        this.scanner = scanner;
        cardMaker = new CardMaker();
        this.database = database;
    }

    public void start() throws InterruptedException, SQLException {

        String choice = "";

        while (!choice.equals("0")) {

            System.out.println(startingMessage());

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cardMaker.orderCard(database);
                    break;
                case "2":

                    System.out.println("\nEnter your card number:");
                    String cardNumber = scanner.nextLine();

                    System.out.println("Enter your pin");
                    String pin = scanner.nextLine();

                    boolean canEnter = checkData(cardNumber, pin);

                    if (canEnter) {
                        System.out.println("\nYou have successfully logged in!\n");
                        choice = loggedInScreen(cardNumber);
                    } else {
                        System.out.println("\nWrong card number or PIN!\n");
                    }

                    break;

                default:
                    break;
            }
        }

        System.out.println("Bye!");
    }

    public String startingMessage() {
        return "1. Create an account\n" +
                "2. Log into account\n" +
                "0.Exit";
    }

    public boolean checkData(String cardNumber, String pin) throws SQLException {

        if (database.cardExists(cardNumber)) {
            String realPin = database.getPin(cardNumber);
            System.out.println("THE REAL PIN" + realPin);
            return realPin.equals(pin);
        }

        return false;
    }

    public String loggedInScreen(String cardNumber) throws SQLException, InterruptedException {

        String choice = "";

        do {

            System.out.println(loggedMenu());

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println(balance(cardNumber));
                    break;
                case "2":
                    addIncome(cardNumber);
                    break;
                case "3":
                    transfer(cardNumber);
                    break;
                case "4":
                    database.remove(cardNumber);
                    System.out.println("The account has been closed!");
                    break;
                default:
                    break;
            }

        } while (!"0".equals(choice));

        return choice;
    }

    private String balance(String cardNumber) throws SQLException {
        return "Balance: " + database.getBalance(cardNumber);
    }

    private void addIncome(String cardNumber) throws SQLException {
        System.out.println("Enter income:");
        int value = Integer.parseInt(scanner.nextLine());

        database.addIncome(cardNumber, value);
    }

    private void transfer(String origin) throws SQLException {
        System.out.println("Enter card number");
        String targetCard = scanner.nextLine();

        if (!database.cardExists(targetCard)) {
            System.out.println("Such card does not exist.");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int value = Integer.parseInt(scanner.nextLine());

        if (!database.isThereEnoughMoney(origin, value)) {
            System.out.println("Not enough money!");
            return;
        }

        database.transfer(origin, targetCard, value);
    }

    private String loggedMenu() {
        return "1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit";
    }
}
