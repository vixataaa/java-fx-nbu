/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem.UIComponents;

import desktoppaymentsystem.Controller;
import desktoppaymentsystem.Services.AuthService;
import desktoppaymentsystem.Services.UserRole;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Vixata
 */
public class MainDashboard implements UIComponent {
    private final AuthService _authService;
    private final Controller _controller;
    
    private HBox createNavigateBtn(String title, String navigateTo) {
        Button btn = new Button(title);

        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                _controller.open(navigateTo);
            }
        });
        
        return hbBtn;
    }
    
    private HBox createLogoutBtn(String title) {
        Button btn = new Button(title);

        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                _authService.logout();
                _controller.reload();
            }
        });
        
        return hbBtn;
    }
    
    public MainDashboard(AuthService authService, Controller controller) {
        _authService = authService;
        _controller = controller;
    }

    @Override
    public Parent render() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Desktop payment system");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        if (_authService.isAuthenticated()) {
            if (_authService.isAuthorized(UserRole.ADMIN)) {
                grid.add(createNavigateBtn("Create store", "create-store"), 0, 4);
                grid.add(createNavigateBtn("Create user", "create-user"), 1, 4);
                grid.add(createNavigateBtn("Reports", "reports"), 2, 4);
            }
            
            grid.add(createNavigateBtn("Create product", "create-product"), 0, 5);
            grid.add(createNavigateBtn("Start sale", "start-sale"), 1, 5);
            
            grid.add(createLogoutBtn("Log out"), 0, 8);
        }
        else {
            grid.add(createNavigateBtn("Log in", "login"), 0, 4);
        }
        
        return grid;
    }
    
}
