/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.UIComponents;

import ViewModels.User;
import ViewModels.Store;
import desktoppaymentsystem.Controller;
import desktoppaymentsystem.Services.UserService;
import desktoppaymentsystem.Services.StoreService;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author Vixata
 */
public class CreateUser implements UIComponent {
    private final UserService _userService;
    private final Controller _controller;
    private final StoreService _storeService;
    
    public CreateUser(UserService userService, StoreService storeService, Controller controller) {
        _userService = userService;
        _storeService = storeService;
        _controller = controller;
    }

    @Override
    public Parent render() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Create user");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField userNameTextField = new TextField();
        grid.add(userNameTextField, 1, 1);

        Label userIdentification = new Label("User identification:");
        grid.add(userIdentification, 0, 2);

        TextField userIdentificationTextField = new TextField();
        grid.add(userIdentificationTextField, 1, 2);
        
        Label userRole = new Label("User role:");
        grid.add(userRole, 0, 3);

        TextField userRoleTextField = new TextField();
        grid.add(userRoleTextField, 1, 3);
        
        Label pw = new Label("Password:");
        grid.add(pw, 0, 4);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 4);        
        
        Label storeName = new Label("Store:");
        grid.add(storeName, 0, 5);
        
        ComboBox<Store> storesComboBox = new ComboBox<Store>();
        storesComboBox.getItems().addAll(_storeService.getStores());
        storesComboBox.setCellFactory(new Callback<ListView<Store>, ListCell<Store>>() {
            @Override
            public ListCell<Store> call(ListView<Store> param) {
                return new ListCell<Store>() {
                    @Override
                    protected void updateItem(Store item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : item.name);
                    }
                };
            }            
        });
        
        grid.add(storesComboBox, 1, 5);
        
        Button createBtn = new Button("Create");
        
        HBox hbBtnCreate = new HBox(10);
        hbBtnCreate.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnCreate.getChildren().add(createBtn);
        grid.add(hbBtnCreate, 0, 6);
        
        createBtn.setOnAction((ActionEvent e) -> {
            User user = new User();
            
            user.name = userNameTextField.getText();
            user.identifier = userIdentificationTextField.getText();
            user.storeId = storesComboBox.getValue().id;
            user.role = userRoleTextField.getText();
            user.password = pwBox.getText();
            
            if (_userService.createUser(user)) {
                _controller.close();
            }
        });
        
        Button closeBtn = new Button("Cancel");
        
        HBox hbBtnClose = new HBox(10);
        hbBtnClose.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnClose.getChildren().add(closeBtn);
        grid.add(hbBtnClose, 1, 6);
        
        closeBtn.setOnAction((ActionEvent e) -> {
            _controller.close();
        });
        
        return grid;
    }
    
}
