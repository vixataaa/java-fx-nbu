/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.UIComponents;

import desktoppaymentsystem.Controller;
import desktoppaymentsystem.Services.StoreService;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
public class CreateStore implements UIComponent {
    private Controller _controller;
    private StoreService _storeService;
    
    public CreateStore(StoreService storeService, Controller controller) {
        _controller = controller;
        _storeService = storeService;
    }

    @Override
    public Parent render() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Create store");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label storeName = new Label("Store Name:");
        grid.add(storeName, 0, 1);

        TextField storeNameTextField = new TextField();
        grid.add(storeNameTextField, 1, 1);

        Label storeAddress = new Label("Store address:");
        grid.add(storeAddress, 0, 2);

        TextField storeAddressTextField = new TextField();
        grid.add(storeAddressTextField, 1, 2);
        
        Button createBtn = new Button("Create");
        
        HBox hbBtnCreate = new HBox(10);
        hbBtnCreate.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnCreate.getChildren().add(createBtn);
        grid.add(hbBtnCreate, 0, 4);
        
        createBtn.setOnAction((ActionEvent e) -> {
            if (_storeService.createStore(storeNameTextField.getText(), storeAddressTextField.getText())) {
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
