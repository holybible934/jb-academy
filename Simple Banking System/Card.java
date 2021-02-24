package banking;

public class Card {
    private final String mCardNumber;
    private final String mPIN;
    private final long mBalance;

    public Card(String cardNum, String pin) {
        this.mCardNumber = cardNum;
        this.mPIN = pin;
        this.mBalance = 0;
    }

    public boolean getUserByCardNum(String cardNum) {
        return cardNum.equals(mCardNumber);
    }

    public boolean verifyPin(String inputPin) {
        System.out.println(inputPin);
        System.out.println(mPIN);
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
