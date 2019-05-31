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
public class PurchaseItem {
    public String identifier;
    public String description;
    public float quantity;
    public float pricePerUnit;
    public float totalPrice;
    
    public String getIdentifier() {
        return identifier;
    }
    
    public String getDescription() {
        return description;
    }
    
    public float getQuantity() {
        return quantity;
    }
    
    public float getPricePerUnit() {
        return pricePerUnit;
    }
    
    public float getTotalPrice() {
        return totalPrice;
    }
}
