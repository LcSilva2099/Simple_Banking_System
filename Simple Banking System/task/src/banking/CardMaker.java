package banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardMaker extends CardFactory{

    private Random random;

    public CardMaker() {
        random = new Random();
    }

    @Override
    public Card createCard(CardDao database) throws SQLException {

        StringBuilder cardNumber = new StringBuilder("400000");

        for (int i = cardNumber.length(); i < 15; i++) {
            cardNumber.append(random.nextInt(10));
        }

        System.out.println(cardNumber);

        cardNumber.append(luhnAlg(cardNumber.toString().split("")));

        StringBuilder pin = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            pin.append(random.nextInt(10));
        }

        Card card = new CustomerCard(cardNumber.toString(), pin.toString());

        database.add(card);

        return card;
    }

    @Override
    int luhnAlg(String[] number) {

        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= number.length; i++) {
            if (i % 2 != 0) {
                numbers.add(Integer.parseInt(number[i - 1]) * 2);
            } else {
                numbers.add(Integer.parseInt(number[i - 1]));
            }
        }

        int finalDigit = 0;
        int sum = 0;

        for (Integer i : numbers) {
            if (i > 9) {
                sum += i - 9;
            } else {
                sum += i;
            }
        }

        if (!(sum % 10 == 0)) {
            finalDigit = sum + (10 - sum % 10);
        } else {
            return finalDigit;
        }

        return finalDigit - sum;
    }
}
