/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.Services;

import ViewModels.Store;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vixata
 */
public class StoreService {
    private final Connection _dbConn;
    
    private boolean isValidStore(String name, String address) {
        return name != null && !name.isEmpty() &&
            address != null && !address.isEmpty();
    }
    
    public StoreService(Connection dbConn) {
        _dbConn = dbConn;
    }
    
    public boolean createStore(String name, String address) {
        if (!isValidStore(name, address)) {
            return false;
        }
        
        try {
            Statement statement = _dbConn.createStatement();
            
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM STORES WHERE name = '%s'", name));
            
            boolean storeFound = result.next();
            
            if (storeFound) {
                return false;
            }
            
            statement.executeUpdate(String.format("INSERT INTO STORES (name, address) VALUES ('%s', '%s');", name, address));
            
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public List<Store> getStores() {
        ArrayList<Store> result = new ArrayList();
        
        try {
            Statement statement = _dbConn.createStatement();
            
            ResultSet queryResult = statement.executeQuery(String.format("SELECT * FROM STORES"));
            
            while (queryResult.next()) {
                Store store = new Store();
                
                store.id = queryResult.getInt("id");
                store.name = queryResult.getString("name");
                store.address = queryResult.getString("address");
                
                result.add(store);
            }
            
            return result;
        }
        catch (Exception ex) {
            return result;
        }
    }
}
