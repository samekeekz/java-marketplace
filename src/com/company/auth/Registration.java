package com.company.auth;

import com.company.entities.User;

import java.sql.SQLException;
import java.util.Scanner;

public class Registration extends User {
    static Scanner in = new Scanner(System.in);

    public Registration(String username, String password, String role) throws SQLException {
        super(username, password, role);
    }

    public static void reg() throws SQLException {
        User user = new User();
        System.out.println("Write username: ");
        String username = in.next();
        user.setUsername(username);
        System.out.println("Write password: ");
        String pass = in.next();
        String correctPass = checkPass(pass);
        user.setPassword(correctPass);
        System.out.println("Write role: 1) seller or 2) buyer");
        int role = in.nextInt();
        switch (role){
            case 1 -> user.setRole("seller");
            case 2 -> user.setRole("buyer");
        }
        user.insert();
        System.out.println("You have successfully registered!");
        logIn.login();
    }
    public static String checkPass(String pass){
        String password = null;
        if(checkCorrectPassword(pass)) return pass;
        else {
            System.out.println("Your password is weak! It should contain both large and small letters and numbers");
            password = in.next();
            checkPass(password);
        }
        return password;
    }
    public static boolean checkCorrectPassword(String password) {
        boolean validOne = false, validTwo = false, validThree = false;
        if (password.length() < 8) return false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if ('a' <= c && c <= 'z') validOne = true;
            if ('0' <= c && c <= '9') validTwo = true;
            if ('A' <= c && c <= 'Z') validThree = true;
            if (validOne && validTwo && validThree) return true;
        }
        return false;
    }
}
