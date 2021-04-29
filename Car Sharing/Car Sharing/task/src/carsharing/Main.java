package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/";
    static Connection conn;
    static Statement stmt;
    static Scanner scanner;

    //  Database credentials
//    static final String USER = "sa";
//    static final String PASS = "";

    public static void main(String[] args) {
        try {
            if (conn == null) {
                connectToDb(args);
            }
        } catch(Exception se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return;
        }
        scanner = new Scanner(System.in);
        printMainMenu();
        int mainOpt = Integer.parseInt(scanner.nextLine());
        while (mainOpt > 0) {
            if (mainOpt == 1) {
                printCompanyMenu();
                int companyOpt = Integer.parseInt(scanner.nextLine());
                switch (companyOpt) {
                    case 1:
                        printCompanyList();
                        break;
                    case 2:
                        System.out.println("Enter the company name:");
                        String newCompanyName = scanner.nextLine();
                        createNewCompany(newCompanyName);
                        break;
                    case 0:
                    default:
                        break;
                }
            } else if (mainOpt == 2) {
                printCustomerList();
            } else if (mainOpt == 3) {
                System.out.println("Enter the customer name:");
                createNewCustomer(scanner.nextLine());
            }
            printMainMenu();
            mainOpt = Integer.parseInt(scanner.nextLine());
        }
        exit();
    }

    private static void printCustomerList() {
        try {
            List<Customer> customerList = new ArrayList<>();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM CUSTOMER;";
            ResultSet customers = stmt.executeQuery(sql);
            if (!customers.isBeforeFirst()) {
                System.out.println("The customer list is empty!");
            } else {
                System.out.println("\nCustomer list:");
                while (customers.next()) {
                    int id = customers.getInt("ID");
                    String companyName = customers.getString("NAME");
                    int rentedCarId = customers.getInt("RENTED_CAR_ID");
                    if (customers.wasNull()) {
                        rentedCarId = -1;
                    }
                    customerList.add(new Customer(id, companyName, rentedCarId));
                }
                IntStream.range(0, customerList.size()).mapToObj(i -> (i + 1) + ". " + customerList.get(i).getName()).forEach(System.out::println);
                System.out.println("0. Back\n");
                int chosenCustomerIndex = Integer.parseInt(scanner.nextLine());
                if (chosenCustomerIndex != 0) {
                    carRentedCustomerAction(customerList.get(chosenCustomerIndex - 1));
                }
            }
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void carRentedCustomerAction(Customer customer) {
        printCustomerMenu();
        int custOpt = Integer.parseInt(scanner.nextLine());
        while (custOpt >= 0) {
            switch (custOpt) {
                case 1:
                    rentCar(customer);
                    break;
                case 2:
                    returnRentedCar(customer);
                    break;
                case 3:
                    printMyRentedCar(customer);
                    break;
                case 0:
                    return;
                default:
                    break;
            }
            printCustomerMenu();
            custOpt = Integer.parseInt(scanner.nextLine());
        }
    }

    private static void rentCar(Customer customer) {
        List<Company> companyList = printCompanyList();
        int companyOpt = Integer.parseInt(scanner.nextLine());
        while (companyOpt > 0) {
            if (companyOpt <= companyList.size()) {
                Company company = companyList.get(companyOpt - 1);
                // TODO: choose a car, then update customer's rented car id
                List<Car> carList = printCarList(company.getId(), "Choose a car:");
                int carOpt = Integer.parseInt(scanner.nextLine());
                while (carOpt > 0) {
                    if (carOpt <= carList.size()) {
                        updateCustomerRentedCarId(customer, carList.get(carOpt));
                    }
                    carOpt = Integer.parseInt(scanner.nextLine());
                }
            }
            companyOpt = Integer.parseInt(scanner.nextLine());
        }
    }

    private static void updateCustomerRentedCarId(Customer customer, Car car) {
        try {
            stmt = conn.createStatement();
            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = " +
                    car.getId() +
                    " WHERE ID = " + customer.getID() + ";";
            stmt.executeUpdate(sql);
            System.out.println("\nYou rented '" + car.getName() + "'");
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void printMyRentedCar(Customer customer) {
        try {
            if (customer.getRentedCarId() == -1) {
                System.out.println("\nYou didn't rent a car!");
            } else {
                stmt = conn.createStatement();
                String sql = "SELECT * FROM CAR WHERE ID = " + customer.getRentedCarId() + ";";
                ResultSet rentedCar = stmt.executeQuery(sql);
                rentedCar.next();
                String carName = rentedCar.getString("NAME");
                int carId = rentedCar.getInt("ID");
                stmt = conn.createStatement();
                sql = "SELECT * FROM COMPANY WHERE ID = " + carId + ";";
                ResultSet companyOfRentedCar = stmt.executeQuery(sql);
                companyOfRentedCar.next();
                String companyName = companyOfRentedCar.getString("NAME");
                System.out.println("\nYour rented car:\n" + carName);
                System.out.println("Company:\n" + companyName);
            }
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void returnRentedCar(Customer customer) {
        try {
            stmt = conn.createStatement();
            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = " + customer.getID() + ";";
            stmt.executeUpdate(sql);
            System.out.println("\nYou've returned a rented car!");
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void printCustomerMenu() {
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
    }

    private static void createNewCustomer(String customerName) {
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO CUSTOMER " +
                    "(NAME) VALUES ('" + customerName + "');";
            int rowCount = stmt.executeUpdate(sql);
            if (rowCount > 0) {
                System.out.println("The customer was added!\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static List<Company> printCompanyList() {
        List<Company> companyList = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM COMPANY;";
            ResultSet companies = stmt.executeQuery(sql);
            if (!companies.isBeforeFirst()) {
                System.out.println("The company list is empty!");
            } else {
                System.out.println("\nChoose a company:");
                while (companies.next()) {
                    int id = companies.getInt("ID");
                    String companyName = companies.getString("NAME");
                    companyList.add(new Company(id, companyName));
                }
                IntStream.range(0, companyList.size()).mapToObj(i -> (i + 1) + ". " + companyList.get(i).getName()).forEach(System.out::println);
                System.out.println("0. Back\n");
                int chosenCompanyIndex = Integer.parseInt(scanner.nextLine());
                if (chosenCompanyIndex != 0) {
                    companyCarAction(companyList.get(chosenCompanyIndex - 1));
                }
            }
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
        }
        return companyList;
    }

    private static void companyCarAction(Company company) {
        printCarsMenu(company.getName());
        int carsOpt = Integer.parseInt(scanner.nextLine());
        while (carsOpt >= 0) {
            switch (carsOpt) {
                case 1:
                    printCarList(company.getId(), "Car list:");
                    break;
                case 2:
                    System.out.println("\nEnter the car name:");
                    createNewCar(company.getId(), scanner.nextLine());
                    break;
                case 0:
                    return;
                default:
                    break;
            }
            printCarsMenu(company.getName());
            carsOpt = Integer.parseInt(scanner.nextLine());
        }
    }

    private static void createNewCar(int companyId, String carName) {
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO CAR " +
                    "(NAME, COMPANY_ID) VALUES ('" + carName + "', " +
                    companyId + ");";
            int rowCount = stmt.executeUpdate(sql);
            if (rowCount > 0) {
                System.out.println("The car was added!\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static List<Car> printCarList(int companyId, String msg) {
        List<Car> carList = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            String sql;
            if ("Car list:".equals(msg)) {
                sql = "SELECT * FROM CAR WHERE COMPANY_ID = " + companyId + " ;";
            } else {
                sql = "SELECT * FROM CAR WHERE COMPANY_ID = " + companyId + "AND RENTED_CAR_ID IS NOT NULL;";
            }
            ResultSet cars = stmt.executeQuery(sql);
            if (!cars.isBeforeFirst()) {
                System.out.println("The car list is empty!\n");
            } else {
                System.out.println("\n" + msg);
                int id;
                while (cars.next()) {
                    id = cars.getInt("ID");
                    String carName = cars.getString("NAME");
                    carList.add(new Car(id, carName, companyId));
                }
                IntStream.range(0, carList.size()).mapToObj(i -> (i + 1) + ". " + carList.get(i).getName()).forEach(System.out::println);
                System.out.println("0. Back\n");
            }
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
        }
        return carList;
    }

    private static void createNewCompany(String newCompanyName) {
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO COMPANY " +
                    "(NAME) VALUES ('" + newCompanyName + "');";
            int rowCount = stmt.executeUpdate(sql);
            if (rowCount > 0) {
                System.out.println("The company was created!\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void printMainMenu() {
        System.out.println("1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit");
    }

    private static void printCompanyMenu() {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
    }

    private static void printCarsMenu(String companyName) {
        System.out.println("'" + companyName + "' company\n" +
                "1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
    }

    private static void exit() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private static void createCompanyTable() throws SQLException {
        // STEP 3: Execute a query
        ResultSet resultSet = conn.getMetaData().getTables(null, null, "COMPANY", null);
        if (!resultSet.next()) {
            System.out.println("Creating COMPANY table in given database...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE COMPANY (" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) NOT NULL UNIQUE" +
                    ");";
            stmt.executeUpdate(sql);
        } else {
            System.out.println("Table COMPANY already exists.");
        }
    }

    private static void createCarTable() throws SQLException {
        ResultSet resultSet = conn.getMetaData().getTables(null, null, "CAR", null);
        if (!resultSet.next()) {
            System.out.println("Creating CAR table in given database...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE CAR (" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) NOT NULL UNIQUE," +
                    "COMPANY_ID INTEGER NOT NULL," +
                    "CONSTRAINT FK_COMPANY_ID FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                    ");";
            stmt.executeUpdate(sql);
        } else {
            System.out.println("Table CAR already exists.");
        }
    }

    private static void createCustomerTable() throws SQLException {
        ResultSet resultSet = conn.getMetaData().getTables(null, null, "CUSTOMER", null);
        if (!resultSet.next()) {
            System.out.println("Creating CUSTOMER table in given database...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE CUSTOMER (" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) NOT NULL UNIQUE," +
                    "RENTED_CAR_ID INTEGER DEFAULT NULL," +
                    "CONSTRAINT FK_RENTED_CAR_ID FOREIGN KEY (RENTED_CARD_ID) REFERENCES CAR(ID)" +
                    ");";
            stmt.executeUpdate(sql);
        } else {
            System.out.println("Table CUSTOMER already exists.");
        }
    }

    private static void connectToDb(String[] args) throws ClassNotFoundException, SQLException {
        // STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);

        // STEP 2: Open a connection
        if (args.length > 0 && "-databaseFileName".equals(args[0])) {
            conn = DriverManager.getConnection(DB_URL + args[1]);
        } else {
            conn = DriverManager.getConnection(DB_URL + "testdb");
        }
        conn.setAutoCommit(true);
//        System.out.println("Connected database successfully...");
        createCompanyTable();
        createCarTable();
        createCustomerTable();
    }
}