package com.company.entities;


import com.company.data.DBconnection;

import java.sql.*;
import java.util.Scanner;

public class Seller extends User {
    public Seller(String username, String password, String role) throws SQLException {
        super(username, password, role);
    }

    public Seller(int id, String username, String role, double balance) throws SQLException {
        super(id, username, role, balance);
    }

    PreparedStatement ps = null;
     Connection conn = DBconnection.connection();
     static ResultSet rs = null;
    static Scanner in = new Scanner(System.in);

    public static void addProduct() throws SQLException {
        System.out.println("Write name of the product: ");
        String name = in.next();
        System.out.println("Price: ");
        double price = in.nextDouble();
        System.out.println("Choose the product category: ");
        Product.infoAllCategory();
        int categoryID = in.nextInt();
        Product product = new Product(name, price, Product.allCategory.get(categoryID-1));
        product.insert();
    }
    public static void showOwnProducts() {
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM products");
            while(rs.next()){
                if(rs.getInt("seller_id") == User.getCurrentUser().getId()) {
                    System.out.println("Category: " + rs.getString("category") + ", ID: " + rs.getInt("id") + ", name: "
                            + rs.getString("name")  + ", price: " + rs.getDouble("price") + "tenge");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    };
}
