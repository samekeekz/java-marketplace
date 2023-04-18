package com.company.entities;


import com.company.data.DBconnection;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Product {
    private String name;
    private Double price;
    private String category;
    public static final List<String> allCategory = Arrays.asList("computers", "gadgets", "appliances",  "automotive",  "products household goods");
    private static Connection connection = DBconnection.connection();
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    static Scanner in = new Scanner(System.in);

    public static void infoAllCategory(){
        for (int i = 0; i<allCategory.size(); i++) {
            System.out.println(i+1 + ") " + allCategory.get(i));
        }
    }
    public Product(String name, Double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public Product() {
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void insert() throws SQLException {
        ps = connection.prepareStatement("INSERT INTO products(name, price, seller_id, category) VALUES (?, ?, ?, ?)");
        ps.setString(1, getName());
        ps.setDouble(2, getPrice());
        ps.setInt(3, User.getCurrentUser().getId());
        ps.setString(4, getCategory());
        ps.execute();
    }
    public static void infoByCategory(String category) throws SQLException {
        connection = DBconnection.connection();
        Statement st = connection.createStatement();
        rs = st.executeQuery("SELECT * FROM products");

        while (rs.next()){
            if(category.equals(rs.getString("category"))){
                System.out.println("ID: " + rs.getInt("id") + ", name: "
                        + rs.getString("name") + ", price: " + rs.getDouble("price")
                        + ", category: " + rs.getString("category"));
            }
        }
    }
}
