package com.company.entities.interfaces;
import com.company.Main;
import java.sql.SQLException;
import static com.company.entities.User.getCurrentUser;

public interface RoleCheck {
    static void check() throws SQLException {
        if(getCurrentUser().getRole().equals("seller")) Main.forTheSeller();
        else Main.forTheBuyer();
    }
}
