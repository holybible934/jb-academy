package carsharing;

public class Customer {
    private int ID;
    private String name;
    private int rentedCarId;

    public Customer(int ID, String name, int rentedCarId) {
        this.ID = ID;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }
}
