package carsharing;

import java.sql.*;
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
            stmt = conn.createStatement();
            String sql = "SELECT * FROM COMPANY;";
            ResultSet companies = stmt.executeQuery(sql);
            if (!companies.isBeforeFirst()) {
                System.out.println("The company list is empty!");
            } else {
                System.out.println("\nChoose a company:");
                while (companies.next()) {
                    System.out.println(companies.getInt("ID") + ". " + companies.getString("NAME"));
                }
                System.out.println("0. Back");
                // TODO: Add Car manipulation
            }
        } catch (SQLException throwables) {
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

    private static void printCompanyMenu() {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
    }

    private static void printMainMenu() {
        System.out.println("1. Log in as a manager\n" +
                "0. Exit");
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

    private static void createCarTable() throws SQLException {
        ResultSet resultSet = conn.getMetaData().getTables(null, null, "CAR", null);
        if (!resultSet.next()) {
            System.out.println("Creating CAR table in given database...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE COMPANY (" +
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
}