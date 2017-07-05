package pk.webdoor.fx.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pk.webdoor.fx.service.ProductService;
import pk.webdoor.model.Product;

public class BillingManagementController implements Initializable{

	
	@FXML private TableColumn<Product, Integer> mID;
	@FXML private TableView<Product> monthsTable;
	
	private ObservableList<Product> productsTableContents;
	
	public ProductService pService = new ProductService();
	
	public BillingManagementController() {
	
	}
	
	private void configureColumns(){
		mID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("prodid"));
		
		System.out.println("mID: " + mID );
		mID.setPrefWidth(75);
		
		productsTableContents = FXCollections.observableList((List<Product>) pService.getAllProducts()); 
		monthsTable.setItems(productsTableContents);
	}
	
	public void showTable(){
		configureColumns();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
        
		System.out.println("location: " + location + " resources " + resources);
		configureColumns();
		
	}

    public void createNewMonth(ActionEvent event) {
    	System.out.println("newCustomerFired Called()....");
    	MessagesController.displayMessage("Fired");
    }

}
