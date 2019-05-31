/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktoppaymentsystem;

import desktoppaymentsystem.UIComponents.Login;
import desktoppaymentsystem.Services.AuthService;
import desktoppaymentsystem.Services.UserService;
import desktoppaymentsystem.Services.ProductsService;
import desktoppaymentsystem.Services.ReportsService;
import desktoppaymentsystem.Services.SalesService;
import desktoppaymentsystem.Services.StoreService;
import desktoppaymentsystem.UIComponents.CreateUser;
import desktoppaymentsystem.UIComponents.CreateProduct;
import desktoppaymentsystem.UIComponents.CreateStore;
import desktoppaymentsystem.UIComponents.MainDashboard;
import desktoppaymentsystem.UIComponents.Reports;
import desktoppaymentsystem.UIComponents.StartSale;
import desktoppaymentsystem.UIComponents.UIComponent;
import java.util.Stack;
import javafx.scene.Scene;

/**
 *
 * @author Vixata
 */
public class Controller {
    private final Scene _scene;
    
    private final AuthService _authService;
    private final StoreService _storeService;
    private final UserService _usersService;
    private final ProductsService _productsService;
    private final SalesService _salesService;
    private final ReportsService _reportsService;

    private final Stack<String> _openedComponents;
    
    public Controller(Scene scene, AuthService authService, StoreService storeService, UserService usersService, ProductsService productsService, SalesService salesService, ReportsService reportsService) {
        _scene = scene;        
        _authService = authService;
        _storeService = storeService;
        _usersService = usersService;
        _productsService = productsService;
        _salesService = salesService;
        _reportsService = reportsService;
        
        _openedComponents = new Stack<>();
        
        open("main-dashboard");
    }
    
    public void close() {
        _openedComponents.pop();
        
        if (_openedComponents.size() != 0) {
            open(_openedComponents.pop());
        }        
    }
    
    public void open(String componentName) {
        switch (componentName) {
            case "main-dashboard": {
                UIComponent component = new MainDashboard(_authService, this);
                
                _openedComponents.add(componentName);
                _scene.setRoot(component.render());
                break;
            }
            case "login": {
                UIComponent component = new Login(_authService, this);
                
                _openedComponents.add(componentName);
                _scene.setRoot(component.render());
                break;
            }
            case "create-store": {
                UIComponent component = new CreateStore(_storeService, this);
                
                _openedComponents.add(componentName);
                _scene.setRoot(component.render());
                break;
            }
            case "create-user": {
                UIComponent component = new CreateUser(_usersService, _storeService, this);
                
                _openedComponents.add(componentName);
                _scene.setRoot(component.render());
                break;
            }
            case "create-product": {
                UIComponent component = new CreateProduct(_productsService, this);
                
                _openedComponents.add(componentName);
                _scene.setRoot(component.render());
                break;
            }
            case "start-sale": {
                UIComponent component = new StartSale(_productsService, _salesService, this);
                
                _openedComponents.add(componentName);
                _scene.setRoot(component.render());
                break;
            }
            case "reports": {
                UIComponent component = new Reports(_reportsService, this);
                
                _openedComponents.add(componentName);
                _scene.setRoot(component.render());
                break;
            }
        }
    }
    
    public void reload() {
        String openedComponent = _openedComponents.pop();
        
        open(openedComponent);
    }
}
