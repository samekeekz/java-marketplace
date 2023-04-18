package com.company.entities;
import com.company.data.DBconnection;

import java.sql.*;
import java.util.ArrayList;
public class Order {
    private static Connection connection = DBconnection.connection();
    public static Array getListOfProducts(int sellerId, int buyerId) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM orders");
        while (rs.next()) {
            if (rs.getInt("sellerID") == sellerId && rs.getInt("buyerID") == buyerId) {
                return rs.getArray("products");
            }
        }
        return null;
    }
    public static void insertOrder(int sellerID, ArrayList<Integer> productsList) throws SQLException {
        Integer[] productsIntegerList = new Integer[productsList.size()];
        for (int i = 0; i < productsList.size(); i++) {
            productsIntegerList[i] = productsList.get(i);
        }
        Array productArray = connection.createArrayOf("INTEGER", productsIntegerList);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO orders  (seller_id, buyer_id, products) values (?, ? ,?)");
        ps.setInt(1, sellerID);
        ps.setInt(2, User.getCurrentUser().getId());
        ps.setArray(3, productArray);
        ps.execute();
        double price = User.getBalance(sellerID) + (Buyer.getAllPrice(productsList)/10);
        updateBalance(price, sellerID);
    }
    public static void updateBalance(Double price, int seller_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE users SET balance = ? WHERE id = ?");
        ps.setDouble(1, price);
        ps.setInt(2, seller_id);
        ps.execute();
    }
}
