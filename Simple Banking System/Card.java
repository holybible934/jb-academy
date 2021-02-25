package banking;

public class Card {
    private final String mCardNumber;
    private final String mPIN;
    private final long mBalance;

    public Card(String cardNum, String pin, long balance) {
        this.mCardNumber = cardNum;
        this.mPIN = pin;
        this.mBalance = balance;
    }

    public boolean verifyPin(String inputPin) {
        return inputPin.equals(mPIN);
    }

    String getmCardNumber() {
        return mCardNumber;
    }

    String getmPIN() {
        return mPIN;
    }

    long getBalance() {
        return mBalance;
    }
}
