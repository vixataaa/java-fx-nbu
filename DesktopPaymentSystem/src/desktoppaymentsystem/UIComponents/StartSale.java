/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.UIComponents;

import ViewModels.Product;
import ViewModels.PurchaseItem;
import desktoppaymentsystem.Controller;
import desktoppaymentsystem.Services.ProductsService;
import desktoppaymentsystem.Services.SalesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Vixata
 */
public class StartSale implements UIComponent {
    private final ProductsService _productsService;
    private final SalesService _salesService;
    private final Controller _controller;
    
    public StartSale(ProductsService productsService, SalesService salesService, Controller controller) {
        _productsService = productsService;
        _salesService = salesService;
        _controller = controller;
    }

    @Override
    public Parent render() {
        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Sales");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label productIdentifier = new Label("Product identifier:");
        grid.add(productIdentifier, 0, 1);

        TextField productIdentifierTextField = new TextField();
        grid.add(productIdentifierTextField, 0, 2);
        
        Label productQuantity = new Label("Quantity:");
        grid.add(productQuantity, 1, 1);

        TextField productQuantityTextField = new TextField();
        grid.add(productQuantityTextField, 1, 2);       
        
        TableView tableView = new TableView();
        
        TableColumn identifierColumn = new TableColumn("Identifier");
        TableColumn descriptionColumn = new TableColumn("Description");
        TableColumn quantityColumn = new TableColumn("Quantity");
        TableColumn unitPriceColumn = new TableColumn("Unit price");
        TableColumn totalPriceColumn = new TableColumn("Total");
        
        identifierColumn.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("identifier"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<PurchaseItem, Float>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<PurchaseItem, Float>("pricePerUnit"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<PurchaseItem, Float>("totalPrice"));
        
        tableView.getColumns().addAll(
            identifierColumn,
            descriptionColumn,
            quantityColumn,
            unitPriceColumn,
            totalPriceColumn
        );        
        
        grid.add(tableView, 0, 4);
        
        final ObservableList<PurchaseItem> tableViewData = FXCollections.observableArrayList();
        
        tableView.setItems(tableViewData);
        
        Label totalText = new Label("Total:");
        Label totalValue = new Label("0");
        
        grid.add(totalText, 1, 4);
        grid.add(totalValue, 2, 4);
        
        Button addBtn = new Button("Add");
        
        HBox hbBtnAdd = new HBox(10);
        hbBtnAdd.setAlignment(Pos.BOTTOM_LEFT);
        hbBtnAdd.getChildren().add(addBtn);
        grid.add(hbBtnAdd, 0, 3);
        
        addBtn.setOnAction((ActionEvent e) -> {
            float quantity = Float.parseFloat(productQuantityTextField.getText());
            Product product = _productsService.getByIdentification(productIdentifierTextField.getText());
            
            if (product == null) {
                return;
            }
            
            PurchaseItem item = new PurchaseItem();
            
            item.description = product.description;
            item.identifier = product.identification;
            item.pricePerUnit = product.pricePerUnit;
            item.quantity = quantity;
            item.totalPrice = quantity * product.pricePerUnit;
            
            totalValue.setText("" + (Float.parseFloat(totalValue.getText()) + item.totalPrice));
            
            tableViewData.add(item);
        });
        
        Button saveBtn = new Button("Save");
        
        HBox hbBtnSave = new HBox(10);
        hbBtnSave.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnSave.getChildren().add(saveBtn);
        grid.add(hbBtnSave, 0, 5);
        
        saveBtn.setOnAction((ActionEvent e) -> {
            if (tableViewData.size() > 0) {
                _salesService.exportReceipt(tableViewData);
                totalValue.setText("0");
                tableViewData.clear();
            }
        });
        
        Button closeBtn = new Button("Cancel");
        
        HBox hbBtnClose = new HBox(10);
        hbBtnClose.setAlignment(Pos.BOTTOM_LEFT);
        hbBtnClose.getChildren().add(closeBtn);
        grid.add(hbBtnClose, 1, 5);
        
        closeBtn.setOnAction((ActionEvent e) -> {
            if (tableViewData.size() > 0) {
                totalValue.setText("0");
                tableViewData.clear();
            }
            else {
                totalValue.setText("0");
                _controller.close();
            }
        });
        
        return grid;
    }
    
}
