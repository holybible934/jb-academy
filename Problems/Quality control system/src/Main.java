import java.util.List;

class QualityControl {

    public static boolean check(List<Box<? extends Bakery>> boxes) {
        // Add implementation here
        if (boxes.isEmpty()) {
            return true;
        }
        try {
            for (Box eachBox : boxes) {
                if (eachBox == null || !(eachBox.get() instanceof Bakery)) {
                    return false;
                }
            }
        } catch (ClassCastException e) {
            return false;
        }
        return true;
    }

}

// Don't change the code below
class Bakery { }

class Cake extends Bakery { }

class Pie extends Bakery { }

class Tart extends Bakery { }

class Paper { }

class Box<T> {

    private T item;

    public void put(T item) {
        this.item = item;
    }

    public T get() {
        return this.item;
    }
}