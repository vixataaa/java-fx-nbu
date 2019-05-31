/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReceiptExporters;

import ViewModels.PurchaseItem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Vixata
 */
public class DBReceiptExporter implements ReceiptExporter {
    private final Connection _dbConn;
    
    public DBReceiptExporter(Connection dbConn) {
        _dbConn = dbConn;
    }

    @Override
    public void export(List<PurchaseItem> items, int userId) {        
        try {
            Statement statement = _dbConn.createStatement();
            
            float total = 0;
            
            for (int i = 0; i < items.size(); i++) {
                total += items.get(i).totalPrice;
            }
            
            statement.executeUpdate(
                String.format(
                    "INSERT INTO RECEIPTS (user_id, total, date) VALUES (%d, %s, NOW());",
                    userId,
                    total
                )
            );
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
