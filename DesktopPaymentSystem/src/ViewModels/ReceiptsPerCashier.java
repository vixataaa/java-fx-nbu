/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

/**
 *
 * @author Vixata
 */
public class ReceiptsPerCashier {
    public String cashierIdentifier;
    public String cashierName;
    public int receiptsProduced;
    
    public String getCashierIdentifier() {
        return cashierIdentifier;
    }
    
    public String getCashierName() {
        return cashierName;
    }
    
    public int getReceiptsProduced() {
        return receiptsProduced;
    }
}
