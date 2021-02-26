package banking;

public class Card {
    private final String mCardNumber;
    private final String mPIN;
    private final int mBalance;

    public Card(String cardNum, String pin, int balance) {
        this.mCardNumber = cardNum;
        this.mPIN = pin;
        this.mBalance = balance;
    }

    public boolean verifyPin(String inputPin) {
        return inputPin.equals(mPIN);
    }

    String getCardNumber() {
        return mCardNumber;
    }

    String getmPIN() {
        return mPIN;
    }

    int getBalance() {
        return mBalance;
    }
}
