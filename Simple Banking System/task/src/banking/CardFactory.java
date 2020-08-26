package banking;

import java.sql.SQLException;

abstract class CardFactory {

    abstract Card createCard(CardDao database) throws SQLException;

    void orderCard(CardDao database) throws SQLException {
        Card card = createCard(database);

        System.out.println("\nYour card has been created");
        System.out.println(card);

    }

    abstract int luhnAlg(String[] number);
}
