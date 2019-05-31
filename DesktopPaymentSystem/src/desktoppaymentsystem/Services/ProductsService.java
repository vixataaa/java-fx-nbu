/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.Services;

import ViewModels.Product;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Vixata
 */
public class ProductsService {
    private final Connection _dbConn;
    
    private boolean isValidProduct(Product product) {
        return product != null && product.description != null && !product.description.isEmpty() &&
            product.identification != null && !product.identification.isEmpty() &&
            product.pricePerUnit > 0;
    }
    
    public ProductsService(Connection dbConn) {
        _dbConn = dbConn;
    }

    public boolean createProduct(Product product) {
        if (!isValidProduct(product)) {
            return false;
        }
        
        try {
            Statement statement = _dbConn.createStatement();
            
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM PRODUCTS WHERE identification = '%s'", product.identification));
            
            boolean productFound = result.next();
            
            if (productFound) {
                return false;
            }
            
            statement.executeUpdate(
                    String.format(
                        "INSERT INTO PRODUCTS (identification, description, price) VALUES ('%s', '%s', %s);",
                        product.identification, 
                        product.description,
                        product.pricePerUnit
                    )
            );
            
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public Product getByIdentification(String identification) {
        Product result = null;
        
        try {
            Statement statement = _dbConn.createStatement();
            
            ResultSet queryResult = statement.executeQuery(String.format("SELECT * FROM PRODUCTS WHERE identification = '%s'", identification));
            
            if (queryResult.next()) {
                result = new Product();
                
                result.description = queryResult.getString("description");
                result.identification = identification;
                result.pricePerUnit = queryResult.getFloat("price");
            }
            
            return result;
        }
        catch (Exception ex) {
            return result;
        }
    }
}
