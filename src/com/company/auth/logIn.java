package com.company.auth;

import com.company.Main;
import com.company.data.DBconnection;
import com.company.entities.Buyer;
import com.company.entities.Seller;
import com.company.entities.User;
import com.company.entities.interfaces.RoleCheck;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class logIn extends User{
    static Connection connection = DBconnection.connection();;
    static Scanner in = new Scanner(System.in);
    public logIn(String username, String password, String role) throws SQLException {
        super(username, password, role);
    }

    public static void login() throws SQLException {
        System.out.println("Write username: ");
        String username = in.next();
        System.out.println("Write password: ");
        String pass = in.next();
        searchUser(username, pass);
    }

    public static void searchUser(String name, String pass) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM users");
        while (rs.next()){
            if ((name.equals(rs.getString("username"))) && pass.equals(rs.getString("password"))) {
                if (rs.getString("role").equals("seller")) {
                    User.setCurrentUser(new Seller(rs.getInt("id"), rs.getString("username"), rs.getString("role"),User.getBalance(rs.getInt("id")))) ;
                }
                else User.setCurrentUser( new Buyer(rs.getInt("id"), rs.getString("username"), rs.getString("role"), User.getBalance(rs.getInt("id"))));
            }
        }
        if(User.getCurrentUser()==null){
            System.out.println("There is no such user or the wrong password!");
            Main.loginMethod();
        }
        RoleCheck.check();
    }
}
