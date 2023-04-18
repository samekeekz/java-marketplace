package com.company.entities;
;

import com.company.data.DBconnection;
import com.company.entities.interfaces.IUser;

import java.rmi.server.RemoteRef;
import java.sql.*;
import java.util.Scanner;

public class User implements IUser {
    private int id;
    private  String role;
    private  String password;
    private String username;
    private double balance = 0;
    static Connection connection = DBconnection.connection();
    static PreparedStatement ps =null;
    static ResultSet rs = null;
    private static User currentUser = null;
    public User() throws SQLException {}
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User user) {
       currentUser = user;
    }

    public User(String username, String password, String role) throws SQLException {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User(int id, String username, String role, double balance) throws SQLException {
        this.id = id;
        this.username = username;
        this.role = role;
        this.balance =balance;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "Username: " + getUsername() + ", role: " + getRole() + ", balance: " + getBalance();
    }
    @Override
    public void insert() throws SQLException {
        ps = connection.prepareStatement("INSERT INTO users (username, password, role, balance) values (?, ? ,?, ?)");
        ps.setString(1, getUsername());
        ps.setString(2, getPassword());
        ps.setString(3, getRole());
        ps.setDouble(4, getBalance());
        ps.executeUpdate();
    }
    static Scanner in = new Scanner(System.in);
    public static void searchByCategory() throws SQLException {
        System.out.println("Select a category: ");
        Product.infoAllCategory();
        int category_id = in.nextInt();
        Product.infoByCategory(Product.allCategory.get(category_id-1));
    }
    public static double getBalance(int id) throws SQLException {
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("SELECT * FROM users");
        while (rs.next()){
            if(id == rs.getInt("id")){
                return rs.getDouble("balance");
            }
        }
        return 0;
    }
}
