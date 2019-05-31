/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem;

import ReceiptExporters.DBReceiptExporter;
import ReceiptExporters.FileReceiptExporter;
import ReceiptExporters.ReceiptExporter;
import desktoppaymentsystem.Services.AuthService;
import desktoppaymentsystem.Services.UserService;
import desktoppaymentsystem.Services.ProductsService;
import desktoppaymentsystem.Services.ReportsService;
import desktoppaymentsystem.Services.SalesService;
import desktoppaymentsystem.Services.StoreService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Vixata
 */
public class DesktopPaymentSystem extends Application {
    
    // Composition root
    @Override
    public void start(Stage primaryStage) {
        // Does not matter
        StackPane root = new StackPane();        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Properties properties = new Properties();
        
        properties.setProperty("user", "root");
        properties.setProperty("password", "newpassword");
                     
        try {
            final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payment_system?useSSL=false", properties);
            
            AuthService authService = new AuthService(conn);
            StoreService storeService = new StoreService(conn);
            UserService userService = new UserService(conn);
            ProductsService productsService = new ProductsService(conn);
            ReportsService reportsService = new ReportsService(conn);
            
            List<ReceiptExporter> exporters = new ArrayList();
            exporters.add(new DBReceiptExporter(conn));
            exporters.add(new FileReceiptExporter());
            
            SalesService salesService = new SalesService(conn, authService, exporters);
            
            Controller controller = new Controller(scene, authService, storeService, userService, productsService, salesService, reportsService);
            
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DesktopPaymentSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
