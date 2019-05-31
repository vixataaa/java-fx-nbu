/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.UIComponents;

import ViewModels.Product;
import desktoppaymentsystem.Controller;
import desktoppaymentsystem.Services.ProductsService;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Vixata
 */
public class CreateProduct implements UIComponent {
    private final ProductsService _productsService;
    private final Controller _controller;
    
    public CreateProduct(ProductsService productsService, Controller controller) {
        _productsService = productsService;
        _controller = controller;
    }

    @Override
    public Parent render() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Create product");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label productDescription = new Label("Product description:");
        grid.add(productDescription, 0, 1);

        TextField productDescriptionTextField = new TextField();
        grid.add(productDescriptionTextField, 1, 1);

        Label productIdentification = new Label("Product identification:");
        grid.add(productIdentification, 0, 2);

        TextField productIdentificationTextField = new TextField();
        grid.add(productIdentificationTextField, 1, 2);
        
        Label productPricePerUnit = new Label("Price per unit:");
        grid.add(productPricePerUnit, 0, 3);

        TextField productPricePerUnitTextField = new TextField();
        grid.add(productPricePerUnitTextField, 1, 3);
        
        Button createBtn = new Button("Create");
        
        HBox hbBtnCreate = new HBox(10);
        hbBtnCreate.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnCreate.getChildren().add(createBtn);
        grid.add(hbBtnCreate, 0, 4);
        
        createBtn.setOnAction((ActionEvent e) -> {
            Product product = new Product();
            
            product.identification = productIdentificationTextField.getText();
            product.description = productDescriptionTextField.getText();
            product.pricePerUnit = Float.parseFloat(productPricePerUnitTextField.getText());
            
            if (_productsService.createProduct(product)) {
                _controller.close();
            }            
        });
        
        Button closeBtn = new Button("Cancel");
        
        HBox hbBtnClose = new HBox(10);
        hbBtnClose.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnClose.getChildren().add(closeBtn);
        grid.add(hbBtnClose, 1, 4);
        
        closeBtn.setOnAction((ActionEvent e) -> {
            _controller.close();
        });
        
        return grid;
    }
    
}
