package banking;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException, SQLException {
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:sqlite:" + args[1];
        CardDao database = new CardDao(url);

        TextUI textUI = new TextUI(scanner, database);

        textUI.start();
    }
}
