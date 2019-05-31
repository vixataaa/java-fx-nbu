/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.Services;

import ViewModels.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Vixata
 */
public class UserService {
    private final Connection _dbConn;
    
    private boolean isValidUser(User user) {
        return user != null && user.name != null && !user.name.isEmpty() &&
            user.password != null && !user.password.isEmpty() &&
            user.identifier != null && !user.identifier.isEmpty() &&
            user.storeId > 0;
    }
    
    public UserService(Connection dbConn) {
        _dbConn = dbConn;
    }

    public boolean createUser(User user) {
        if (!isValidUser(user)) {
            return false;
        }
        
        try {
            Statement statement = _dbConn.createStatement();
            
            ResultSet userQueryResult = statement.executeQuery(String.format("SELECT * FROM USERS WHERE identifier = '%s'", user.identifier));
            
            boolean userFound = userQueryResult.next();
            
            if (userFound) {
                return false;
            }
            
            ResultSet storeQueryResult = statement.executeQuery(String.format("SELECT * FROM STORES WHERE id = %d", user.storeId));
            
            boolean storeFound = storeQueryResult.next();
            
            if (!storeFound) {
                return false;
            }
            
            statement.executeUpdate(
                    String.format("INSERT INTO USERS (name, identifier, role, password, store_id) VALUES ('%s', '%s', '%s', '%s', %d);",
                    user.name,
                    user.identifier,
                    user.role,
                    user.password,
                    user.storeId
                )
            );
            
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
