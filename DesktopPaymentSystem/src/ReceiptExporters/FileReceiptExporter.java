/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReceiptExporters;

import ViewModels.PurchaseItem;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vixata
 */
public class FileReceiptExporter implements ReceiptExporter {
    private String getSpaces(int count) {
        String result = "";
        
        for (int i = 0; i < count; i++) {
            result += " ";
        }
        
        return result;
    }
    
    public FileReceiptExporter() {
        
    }

    @Override
    public void export(List<PurchaseItem> items, int userId) {
        StringBuffer buffer = new StringBuffer();
        
        String descriptionText = "Description";
        String quantityText = "Quantity";
        String totalText = "Total";
        
        int maxDescriptionLength = descriptionText.length();
        float totalPrice = 0;
        
        for (int i = 0; i < items.size(); i++) {
            PurchaseItem currentItem = items.get(i);
            
            maxDescriptionLength = Math.max(currentItem.description.length(), maxDescriptionLength);
            totalPrice += currentItem.totalPrice;
        }
        

        
        buffer.append(
            String.format(
                "%s%s%s%s%s%s%s",
                descriptionText,
                getSpaces(maxDescriptionLength - descriptionText.length() + 4),
                quantityText,
                getSpaces(4),
                totalText,
                System.lineSeparator(),
                System.lineSeparator()
            )
        );
                
        for (int i = 0; i < items.size(); i++) {
            PurchaseItem currentItem = items.get(i);
            
            buffer.append(
                String.format(
                    "%s%s%.2f%s%.2f%s",
                    currentItem.description,
                    getSpaces(maxDescriptionLength - currentItem.description.length() + 4),
                    currentItem.quantity,
                    getSpaces(quantityText.length()),
                    currentItem.totalPrice,
                    System.lineSeparator()
                )
            );
        }
        
        buffer.append(String.format("%sTotal: %.2f", System.lineSeparator(), totalPrice));
        
        String result = buffer.toString();
        
        BufferedWriter writer = null;
        try
        {
            LocalDateTime now = LocalDateTime.now();
            
            writer = new BufferedWriter(
                    new FileWriter(
                        String.format(
                            "%s-%s-%s_%s-%s-%s.txt",
                            now.getDayOfMonth(),
                            now.getMonthValue(),
                            now.getYear(),
                            now.getHour(),
                            now.getMinute(),
                            now.getSecond()
                        )
                    )
            );
            writer.write(result);

        }
        catch ( IOException e)
        {
        }
        finally
        {
            try
            {
                if ( writer != null)
                writer.close();
            }
            catch ( IOException e)
            {
            }
        }
        
        
        
    }    
}
