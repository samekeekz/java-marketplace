package com.company.entities.interfaces;
import com.company.Main;
import com.company.entities.User;
import java.sql.SQLException;

public interface Exit {
    static void exit() throws SQLException {
        User.setCurrentUser(null);
        Main.loginMethod();
    }
}
