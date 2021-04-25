package carsharing;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

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
        try{
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
                    printMainMenu();
                    mainOpt = Integer.parseInt(scanner.nextLine());
                    break;
            }
        }
        exit();
    }

    private static void printCompanyList() {
        try {
            HashMap<Integer, String> companyMap = new HashMap<>();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM COMPANY;";
            ResultSet companies = stmt.executeQuery(sql);
            if (!companies.isBeforeFirst()) {
                System.out.println("The company list is empty!");
            } else {
                System.out.println("\nChoose a company:");
                int id = 0;
                while (companies.next()) {
                    id = companies.getInt("ID");
                    String companyName = companies.getString("NAME");
                    System.out.println(id + ". " + companyName);
                    companyMap.put(id, companyName);
                }
                System.out.println("0. Back");
                int option = Integer.parseInt(scanner.nextLine());
                if (option != 0) {
                    companyCarAction(id, companyMap.get(id));
                }
            }
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void companyCarAction(int companyId, String companyName) {
        printCarsMenu(companyName);
        int carsOpt = Integer.parseInt(scanner.nextLine());
        while (carsOpt >= 0) {
            switch (carsOpt) {
                case 1:
                    printCarList(companyId);
                    break;
                case 2:
                    createNewCar(companyId, scanner.nextLine());
                    break;
                case 0:
                    return;
                default:
                    break;
            }
            printCarsMenu(companyName);
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
            stmt = conn.createStatement();
            String sql = "SELECT * FROM CAR;";
            ResultSet cars = stmt.executeQuery(sql);
            if (!cars.isBeforeFirst()) {
                System.out.println("The car list is empty!");
            } else {
                System.out.println("\nCar list:");
                int id = 0;
                while (cars.next()) {
                    id = cars.getInt("ID");
                    String companyName = cars.getString("NAME");
                    System.out.println(id + ". " + companyName);
                }
                System.out.println("0. Back");
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
                    "NAME VARCHAR(255) NOT NULL UNIQUE" +
                    "COMPANY_ID INTEGER NOT NULL" +
                    "ADD CONSTRAINT FK_COMPANY_ID FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
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
    }
}