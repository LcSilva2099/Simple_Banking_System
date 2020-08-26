package banking;

abstract class Card {

    String number;
    String pin;

    public Card(String number, String pin) {
        this.number = number;
        this.pin = pin;
    }

    abstract String getNumber();

    abstract String getPin();

    @Override
    public String toString() {
        return "Your card number:\n" + number + "\nYour card PIN:\n" + pin + "\n";
    }
}
