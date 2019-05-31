/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.UIComponents;

import ViewModels.Product;
import ViewModels.PurchaseItem;
import ViewModels.ReceiptsPerCashier;
import ViewModels.Report;
import desktoppaymentsystem.Controller;
import desktoppaymentsystem.Services.ReportsService;
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
public class Reports implements UIComponent {    
    private final ReportsService _reportsService;
    private final Controller _controller;
    
    public Reports(ReportsService reportsService, Controller controller) {
        _reportsService = reportsService;
        _controller = controller;
    }

    @Override
    public Parent render() {
        Report report = _reportsService.getReport();
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Reports");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label productIdentifier = new Label("Total receipts:");
        grid.add(productIdentifier, 0, 1);

        TextField productIdentifierTextField = new TextField();
        grid.add(productIdentifierTextField, 0, 2);
        productIdentifierTextField.setText("" + report.receiptsProduced);
        
        Label productQuantity = new Label("Total receipts earned:");
        grid.add(productQuantity, 1, 1);

        TextField productQuantityTextField = new TextField();
        grid.add(productQuantityTextField, 1, 2);       
        productQuantityTextField.setText("" + report.totalReceiptEarned);
        
        TableView tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn identifierColumn = new TableColumn("Cashier identifier");
        TableColumn descriptionColumn = new TableColumn("Cashier name");
        TableColumn quantityColumn = new TableColumn("Total receipts");
        
        identifierColumn.setCellValueFactory(new PropertyValueFactory<ReceiptsPerCashier, String>("cashierIdentifier"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<ReceiptsPerCashier, String>("cashierName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<ReceiptsPerCashier, Integer>("receiptsProduced"));
                
        tableView.getColumns().addAll(
            identifierColumn,
            descriptionColumn,
            quantityColumn
        );        
        
        grid.add(tableView, 0, 4);
        
        final ObservableList<ReceiptsPerCashier> tableViewData = FXCollections.observableArrayList(report.receiptsPerCashier);
        
        tableView.setItems(tableViewData);
        
        Button closeBtn = new Button("Cancel");
        
        HBox hbBtnClose = new HBox(10);
        hbBtnClose.setAlignment(Pos.BOTTOM_LEFT);
        hbBtnClose.getChildren().add(closeBtn);
        grid.add(hbBtnClose, 1, 5);
        
        closeBtn.setOnAction((ActionEvent e) -> {
            _controller.close();
        });
        
        return grid;
    }    
}
