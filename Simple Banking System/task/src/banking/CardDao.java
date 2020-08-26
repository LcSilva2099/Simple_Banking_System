package banking;

import java.sql.*;

public class CardDao {

    private String databasePath;

    public CardDao(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getPin(String cardNumber) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {
            PreparedStatement statement = connection.prepareStatement("SELECT pin FROM card WHERE number = ?");
            statement.setString(1, cardNumber);
            ResultSet rs = statement.executeQuery();

            return rs.getString(1);
        }
    }

    public void add(Card card) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO card(number,pin) VALUES(?,?)");
            statement.setString(1, card.getNumber());
            statement.setString(2, card.getPin());
            statement.executeUpdate();
        }
    }

    public void remove(String cardNumber) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM card WHERE number = ?");
            statement.setString(1, cardNumber);
            statement.executeUpdate();
            System.out.println("The account has been closed!");
        }
    }

    public String getBalance(String cardNumber) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {
            PreparedStatement statement = connection.prepareStatement("SELECT balance FROM card WHERE number = ?");
            statement.setString(1, cardNumber);
            ResultSet rs = statement.executeQuery();

            return rs.getString(1);
        }
    }

    public void addIncome(String cardNumber, int value) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE Card SET balance = balance + ? WHERE number = ?");
            statement.setInt(1, value);
            statement.setString(2, cardNumber);
            statement.executeUpdate();
            System.out.println("Success!");
        }
    }

    public void transfer(String origin, String targetCard, int value) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {

            PreparedStatement statement = connection.prepareStatement("UPDATE Card SET balance = balance - ? WHERE number = ?");
            statement.setInt(1, value);
            statement.setString(2, origin);
            statement.executeUpdate();

            statement = connection.prepareStatement("UPDATE Card SET balance = balance + ? WHERE number = ?");
            statement.setInt(1, value);
            statement.setString(2, targetCard);
            statement.executeUpdate();

            System.out.println("Success!");
        }
    }

    public boolean isThereEnoughMoney(String cardNumber, int value) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {

            PreparedStatement statement = connection.prepareStatement("SELECT balance FROM Card WHERE number = ?");
            statement.setString(1, cardNumber);
            ResultSet rs = statement.executeQuery();

            return value >= Integer.parseInt(String.valueOf(rs.next()));
        }
    }

    public Boolean cardExists(String cardNumber) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {

            PreparedStatement statement = connection.prepareStatement("SELECT number FROM Card WHERE number = ?");
            statement.setString(1, cardNumber);
            ResultSet rs = statement.executeQuery();

            return rs.next();
        }
    }

    private Connection createConnectionAndEnsureDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection(databasePath);
        try {
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY," +
                    "number TEXT," +
                    "pin TEXT," +
                    "balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
