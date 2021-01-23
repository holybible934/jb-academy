class Primitive {
    public static boolean toPrimitive(Boolean b) {
        boolean result;
        try {
            result = b;
        } catch (Exception ex) {
            return false;
        }
        return result;
    }
}