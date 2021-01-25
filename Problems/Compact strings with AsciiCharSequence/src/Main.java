import java.util.*;

class AsciiCharSequence implements CharSequence {
    // implementation
    private final byte[] charSeq;

    public AsciiCharSequence(byte[] arr) {
        charSeq = arr.clone();
    }
    @Override
    public int length() {
        return charSeq.length;
    }
    @Override
    public char charAt(int index) {
        return (char) charSeq[index];
    }
    @Override
    public AsciiCharSequence subSequence(int start, int end) {
        return new AsciiCharSequence(Arrays.copyOfRange(charSeq, start, end));
    }
    @Override
    public String toString() {
        return new String(charSeq);
    }
}