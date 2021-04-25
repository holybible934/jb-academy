package carsharing;

public class Car {
    private int id;
    private int company_id;
    private String name;

    public Car(int id, String name, int company_id) {
        this.id = id;
        this.company_id = company_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }


}
