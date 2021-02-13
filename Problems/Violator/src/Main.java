import java.util.ArrayList;

/**
 * Class to work with
 */
class Violator {

    public static List<Box<? extends Bakery>> defraud() {
        // Add implementation here
        Box box = new Box();
        box.put(new Paper());
        List list = new ArrayList();
        list.add(box);
        return list;
    }

}