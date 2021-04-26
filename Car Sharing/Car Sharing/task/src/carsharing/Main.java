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
                // TODO: Login as a customer
            } else if (mainOpt == 3) {
                System.out.println("Enter the customer name:");
                createNewCustomer(scanner.nextLine());
            }
            printMainMenu();
            mainOpt = Integer.parseInt(scanner.nextLine());
        }
        exit();
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

    private static void printCompanyList() {
        try {
            List<Company> companyList = new ArrayList<>();
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
    }

    private static void companyCarAction(Company company) {
        printCarsMenu(company.getName());
        int carsOpt = Integer.parseInt(scanner.nextLine());
        while (carsOpt >= 0) {
            switch (carsOpt) {
                case 1:
                    printCarList(company.getId());
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

    private static void printCarList(int companyId) {
        try {
            List<Car> carList = new ArrayList<>();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM CAR WHERE COMPANY_ID = " + companyId + " ;";
            ResultSet cars = stmt.executeQuery(sql);
            if (!cars.isBeforeFirst()) {
                System.out.println("The car list is empty!\n");
            } else {
                System.out.println("\nCar list:");
                int id = 0;
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
                    "RENTED_CARD_ID INTEGER DEFAULT NULL," +
                    "CONSTRAINT FK_RENTED_CAR_ID FOREIGN KEY (RENTED_CARD_ID) REFERENCES CAR(ID)" +
                    ");";
            stmt.executeUpdate(sql);
        } else {
            System.out.println("Table CAR already exists.");
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