package banking;

public class BankingUser {
    private String mCardNumber;
    private String mPIN;
    private long mBalance;

    public BankingUser(String cardNum, String pin) {
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

    public long getBalance() {
        return mBalance;
    }
}
