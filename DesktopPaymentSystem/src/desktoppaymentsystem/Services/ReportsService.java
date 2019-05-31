/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.Services;

import ViewModels.ReceiptsPerCashier;
import ViewModels.Report;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Vixata
 */
public class ReportsService {
    private final Connection _dbConn;
    
    public ReportsService(Connection dbConn) {
        _dbConn = dbConn;
    }
    
    public Report getReport() {    
        Report result = new Report();
        
        try {
            Statement statement = _dbConn.createStatement();
            
            ResultSet receiptCountQueryResult = statement.executeQuery(String.format("SELECT COUNT(*) as count FROM receipts"));
            
            if (receiptCountQueryResult.next()) {
                result.receiptsProduced = receiptCountQueryResult.getInt("count");
            }
            
            ResultSet receiptTotalSumQueryResult = statement.executeQuery(String.format("SELECT SUM(total) as total FROM receipts"));
            
            if (receiptTotalSumQueryResult.next()) {
                result.totalReceiptEarned = receiptTotalSumQueryResult.getFloat("total");
            }
            
            
            ResultSet receiptsPerUserQueryResult = statement.executeQuery(
                String.format(
                    "SELECT U.identifier, U.name, COUNT(*) as receiptsCount\n" +
                    "FROM receipts\n" +
                    "	JOIN users AS U\n" +
                    "		ON user_id = U.id\n" +
                    "GROUP BY user_id\n" +
                    "ORDER BY user_id"
                )
            );
            
            result.receiptsPerCashier = new ArrayList<ReceiptsPerCashier>();
            
            while (receiptsPerUserQueryResult.next()) {
                ReceiptsPerCashier currentUserReceipts = new ReceiptsPerCashier();
                
                currentUserReceipts.cashierIdentifier = receiptsPerUserQueryResult.getString("identifier");
                currentUserReceipts.cashierName = receiptsPerUserQueryResult.getString("name");
                currentUserReceipts.receiptsProduced = receiptsPerUserQueryResult.getInt("receiptsCount");
                
                result.receiptsPerCashier.add(currentUserReceipts);
            }
            
            return result;
        }
        catch (Exception ex) {
            return result;
        }
    }
}
