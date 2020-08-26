package banking;

public class CustomerCard extends Card{

    public CustomerCard(String number, String pin) {
        super(number, pin);
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getPin() {
        return pin;
    }
}
