package com.company.entities;

import com.company.Main;
import com.company.data.DBconnection;
import com.company.entities.interfaces.RoleCheck;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Buyer extends User{
    private static Connection connection = DBconnection.connection();
    private static PreparedStatement ps = null;;
    private static ResultSet productDatas = null;
    static Scanner in = new Scanner(System.in);
    public Buyer(int id, String username, String role, double balance) throws SQLException {
        super(id, username, role, balance);
    }
    public static void printListOfProducts() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM products");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + ") " + rs.getString("name") + ". Price: " + rs.getInt("price") + "₸ , category: " + rs.getArray("category"));
        }
        System.out.println("");
    }
    public static double getAllPrice(ArrayList<Integer> productIDs) throws SQLException {
        long counter = 0;
        Statement st = connection.createStatement();
        productDatas = st.executeQuery("SELECT * from products");
        while (productDatas.next()) {
            for (Integer productID : productIDs) {
                if (productDatas.getInt("id") == productID) {
                    counter += productDatas.getInt("price");
                }
            }
        }
        return counter;
    }

    public static void buyProduct(ArrayList<Integer> productIDs) throws SQLException {
        Statement st = connection.createStatement();
        Statement stProducts = connection.createStatement();
        ResultSet userDatas = st.executeQuery("SELECT * FROM users");

        while (userDatas.next()) {
            ArrayList<Integer> productArray = new ArrayList<>();
            for (Integer productID : productIDs) {
                productDatas = stProducts.executeQuery("SELECT * FROM products");
                while (productDatas.next()) {
                    if (productDatas.getInt("id") == productID) {
                        if (productDatas.getInt("seller_id") == userDatas.getInt("id")) {
                            productArray.add(productID);
                        }
                    }
                }
            }
            if (productArray.isEmpty()) {
                continue;
            }
            Order.insertOrder(userDatas.getInt("id"), productArray);
        }
    }

    public static void buyProductMenu() throws SQLException {
        System.out.println("How many products are you want to buy?");
        ArrayList<Integer> idsOfProducts = new ArrayList<>();
        int numberOfProducts = in.nextInt();
        int cnt = 0;
        while (cnt++ != numberOfProducts) {
            System.out.print(cnt + ") ");
            int id = in.nextInt();
            idsOfProducts.add(id);
        }
        if(User.getCurrentUser().getBalance() - Buyer.getAllPrice(idsOfProducts)<0){
            System.out.println("Insufficient funds");
            Main.forTheBuyer();
        }
        Buyer.buyProduct(idsOfProducts);
        System.out.println("All price: " + Buyer.getAllPrice(idsOfProducts));
        updateBalance(User.getCurrentUser().getBalance() - Buyer.getAllPrice(idsOfProducts));
        User.getCurrentUser().setBalance(User.getCurrentUser().getBalance() - Buyer.getAllPrice(idsOfProducts));
        System.out.println("Thank you for your purchase!!! \n\n");
    }
    public static void topUp() throws SQLException {
        System.out.println("Write the amount of money");
        double balance = in.nextDouble();
        User.getCurrentUser().setBalance(User.getBalance(User.getCurrentUser().getId())+balance);
        updateBalance(User.getCurrentUser().getBalance());
        System.out.println(User.getCurrentUser());
        RoleCheck.check();
    }
    public static void updateBalance(Double balance) throws SQLException {
        ps = connection.prepareStatement("UPDATE users SET balance = ? WHERE id = ?");
        ps.setDouble(1, balance);
        ps.setInt(2, User.getCurrentUser().getId());
        ps.execute();
    }
}