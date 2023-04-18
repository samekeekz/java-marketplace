package com.company;

import com.company.auth.Registration;
import com.company.auth.logIn;
import com.company.entities.Buyer;
import com.company.entities.Product;
import com.company.entities.Seller;
import com.company.entities.User;
import com.company.entities.interfaces.Exit;
import com.company.entities.interfaces.RoleCheck;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {
        loginMethod();
    }
    static Scanner in = new Scanner(System.in);
    public static void loginMethod() throws SQLException {
        System.out.println("""
                    1) Log in
                    2) Registration
                    """);
        int menu = in.nextInt();
        switch (menu){
            case 1 -> logIn.login();
            case 2 -> Registration.reg();
        }
    }
    public static void forTheSeller() throws SQLException {
        System.out.println("""
                    1) Add a product
                    2) List of products
                    3) Search by category
                    4)  My account
                    5) Exit
                    """);
        int menu = in.nextInt();
        switch (menu){
            case 1 -> Seller.addProduct();
            case 2 -> Seller.showOwnProducts();
            case 3 -> User.searchByCategory();
            case 4 -> {
                System.out.println(User.getCurrentUser());
                forTheSeller();
            }
            case 5 -> Exit.exit();
        }
        System.out.println("\n");
        forTheSeller();
    }
    public static void forTheBuyer() throws SQLException {
        System.out.println("""
                    1) List of products
                    2) Buy products
                    3) Search by category
                    4) Top up your balance
                    5) My account
                    6) Exit
                    """);
        int menu = in.nextInt();
        switch (menu){
            case 1 -> Buyer.printListOfProducts();
            case 2 -> Buyer.buyProductMenu();
            case 3 -> User.searchByCategory();
            case 4 -> Buyer.topUp();
            case 5 -> {
                System.out.println(User.getCurrentUser());
                forTheBuyer();
            }
            case 6 -> Exit.exit();
        }
        forTheBuyer();
    }

}