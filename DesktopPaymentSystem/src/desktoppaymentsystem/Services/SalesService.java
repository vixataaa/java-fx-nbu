/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.Services;

import ReceiptExporters.ReceiptExporter;
import ViewModels.PurchaseItem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Vixata
 */
public class SalesService {
    private final Connection _dbConn;
    private final AuthService _authService;
    private final List<ReceiptExporter> _exporters;
    
    public SalesService(Connection dbConn, AuthService authService, List<ReceiptExporter> exporters) {
        _dbConn = dbConn;
        _authService = authService;
        _exporters = exporters;
    }
    
    public void exportReceipt(List<PurchaseItem> items) {        
        try {
            Statement statement = _dbConn.createStatement();
            
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM USERS WHERE identifier = '%s'", _authService.getLoggedIdentifier()));
            
            boolean userFound = result.next();
            
            if (userFound) {
                int userId = result.getInt("id");
                
                for (int i = 0; i < _exporters.size(); i++) {
                    _exporters.get(i).export(items, userId);
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
