/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.concurrent.Task;

/**
 *
 * @author Vixata
 */
public class AuthService {
    private String _identifier;
    private UserRole _role;
    private Connection _dbConn;
    
    private boolean isValidLoginAttempt(String identifier, String password) {
        return identifier != null && !identifier.isEmpty() &&
            password != null && !password.isEmpty();
    }
    
    public AuthService(Connection dbConn) {
        _identifier = null;
        _role = UserRole.UNAUTHORIZED;
        _dbConn = dbConn;
    }
    
    public String getLoggedIdentifier() {
        return _identifier;
    }
    
    public boolean login(String identifier, String password) {        
        if (!isValidLoginAttempt(identifier, password)) {
            return false;
        }
        
        try {
            Statement statement = _dbConn.createStatement();
            
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM USERS WHERE identifier = '%s' AND password = '%s'", identifier, password));
            
            boolean userFound = result.next();
            
            if (userFound) {
                if (result.getString("role").equals("admin")) {
                    _role = UserRole.ADMIN;
                }
                else {
                    _role = UserRole.CASHIER;
                }
                
                _identifier = identifier;
            }
            
            return userFound;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public void logout() {
        _identifier = null;
        _role = UserRole.UNAUTHORIZED;
    }
    
    public boolean isAuthenticated() {
        return !_role.equals(UserRole.UNAUTHORIZED);
    }
    
    public boolean isAuthorized(UserRole role) {
        return isAuthenticated() && role.equals(_role);
    }
}
