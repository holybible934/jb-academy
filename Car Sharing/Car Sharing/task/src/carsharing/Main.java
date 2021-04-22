package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/";
    static Connection conn;
    static Statement stmt;

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

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
        Scanner scanner = new Scanner(System.in);
        printMainMenu();
        int mainOpt = Integer.parseInt(scanner.nextLine());
        while (mainOpt > 0) {
            int compantOpt = Integer.parseInt(scanner.nextLine());
            printCompanyMenu();
            switch (compantOpt) {
                case 1:
                    break;
                case 2:
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

    private static void createNewCompany(String newCompanyName) {
        try {
            stmt = conn.createStatement();
            String sql = "INSERT TABLE COMPANY " +
                    "VALUES(" + newCompanyName + ");";
            int rowCount = stmt.executeUpdate(sql);
            if (rowCount > 0) {
                System.out.println("The company was created!");
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
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void createCompanyTable() throws SQLException {
        // STEP 3: Execute a query
        System.out.println("Creating table in given database...");
        stmt = conn.createStatement();
        String sql = "CREATE TABLE COMPANY " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT " +
                "NAME VARCHAR(255)) NOT NULL UNIQUE";
        stmt.executeUpdate(sql);
    }

    private static void connectToDb(String[] args) throws ClassNotFoundException, SQLException {
        // STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);

        // STEP 2: Open a connection
        if ("-databaseFileName".equals(args[0])) {
            conn = DriverManager.getConnection(DB_URL + args[1]);
        } else {
            conn = DriverManager.getConnection(DB_URL + "testdb");
        }
        conn.setAutoCommit(true);
//        System.out.println("Connected database successfully...");
        createCompanyTable();
    }
}