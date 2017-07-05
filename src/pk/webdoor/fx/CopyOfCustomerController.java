package pk.webdoor.fx;


import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import pk.webdoor.fx.controllers.CopyOfInvoicesController;
import pk.webdoor.fx.controllers.MessagesController;
import pk.webdoor.fx.exceptions.CustomerDeleteException;
import pk.webdoor.fx.exceptions.CustomerUpdateException;
import pk.webdoor.fx.service.CustomerService;
import pk.webdoor.fx.service.PackageService;
import pk.webdoor.fx.service.ProductService;
import pk.webdoor.model.Customer;
import pk.webdoor.model.ICustomer;
import pk.webdoor.model.InvoiceToCust;
import pk.webdoor.model.Product;
import pk.webdoor.model.WDPackage;

public class CopyOfCustomerController implements Initializable {
	
    @FXML AnchorPane details;
    @FXML Button newCustomer;
    @FXML Button deleteCustomer;
    @FXML Button saveCustomer;
    
    @FXML private TextField filterField;
    
    @FXML private TableView<Customer> table;

    @FXML private TableColumn<Customer, String> colName;
    @FXML private TableColumn<Customer, String> colFathername;
 
    @FXML TextField cid;
    @FXML TextField ckey;
    @FXML TextField cnic;
    @FXML TextField name;
    @FXML TextField fathername;
    @FXML TextField username;
    @FXML TextField password;
    @FXML TextField zone;
    @FXML TextField dor;
    @FXML TextField telephone;
    @FXML TextField mobile;
    @FXML TextField email;
    @FXML TextField address;
        @FXML
    TextField customerPackage;
    @FXML ChoiceBox<Integer> packagesList;
    @FXML TextArea comments;
    
    private ObservableList<Integer> packagesContent;
    
    /*
    @FXML ListView<Integer> packagesView;
    private ObservableList<Integer> packagesContent;
    */
   
//    @FXML private TableView<InvoiceToCust> invoicesTable;
//    @FXML private GridPane invoiceDetails;
/*    
    @FXML private TableColumn<InvoiceToCust, Integer> invoiceID;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceMonth;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceIssueDate;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceDueDate;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceSum;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceAfterDue;
 */   
    @FXML TextField IIdDetailsText;
    @FXML TextField IPaidOnDetailsText;
    @FXML TextField IStatusDetailsText;
    @FXML TextField IAmountDetailsText;
    @FXML Button printButton;
    @FXML Button updateInvoiceButton;
    @FXML Button deleteInvoiceButton;
    @FXML TabPane customerTabs;
    
    @FXML private TableView<Product> productsTable;
    
    @FXML private TableColumn<Product, Integer> pID;
    @FXML private TableColumn<Product, String> pMonth;
    @FXML private TableColumn<Product, String> pIssueDate;
    @FXML private TableColumn<Product, String> pDueDate;
    @FXML private TableColumn<Product, Integer> pPayment;
    @FXML private TableColumn<Product, Integer> pFine;
    
    private ObservableList<Product> productsTableContents;
    
    
    private CustomerService service = new CustomerService();
    
    public ProductService pService = new ProductService();
    
    private PackageService packageService = new PackageService();
        
    
    //private ObservableList<Customer> tableContent = FXCollections.observableList((List<Customer>) service.getCustomers());
    //private ObservableList<Customer> tableContent;
    private ObservableList<Customer> tableContent = FXCollections.observableArrayList();
    private ObservableList<Customer> filteredData = FXCollections.observableArrayList();
    
    public ObservableList<InvoiceToCust> invoicesContent = FXCollections.observableArrayList();

    
    
	private FadeTransition fadeOutTransition;

	private FadeTransition fadeInTransition;
	
	//private SingleSelectionModel<Tab> tabsSelection = customerTabs.getSelectionModel();
	
//	@FXML private CopyOfInvoicesController invoicesContentTab;
		
	public CopyOfCustomerController(){
		tableContent.addAll(service.getCustomers());
		filteredData.addAll(tableContent);
		
		tableContent.addListener(new ListChangeListener<Customer>() {
	          @Override
	          public void onChanged(ListChangeListener.Change<? extends Customer> change) {
	              updateFilteredData();
	          }
	      });
		
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rsrcs) {
		assert newCustomer != null;
		assert deleteCustomer != null;
		assert saveCustomer != null;
		assert table != null;
		assert colName != null;
		assert colFathername != null;

		
        //configureButtons();
        configureTabs();
        configureCustomerDetails();
        //configureInvoiceDetails();
        configureCustomerTable();
        //configureInvoiceTable();
        //configureProductsTable();

	}
	
	public static DataFormat dataFormat =  new DataFormat("mycell");
	private Product dragProduct = null;



	private void configureCustomerTable() {
        
        colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        colFathername.setCellValueFactory(new PropertyValueFactory<Customer, String>("fathername"));
        
        colName.setPrefWidth(75);
        colFathername.setPrefWidth(75);
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //tableContent = getTableContents();   
        //tableContent = (List<Customer>) service.getCustomers(); 
        
//        table.setItems(getCustomerList());
        table.setItems(filteredData);

        
        filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                
                updateFilteredData();
            }
        });
        
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.getSelectionModel().selectedItemProperty().addListener(customerChangeListener());
//        table.getSelectionModel().selectedItemProperty().addListener(invoicesContentTab);

        
        assert table.getItems() == tableContent;
        
		fadeOutTransition = new FadeTransition(Duration.millis(500), details);
        fadeOutTransition.setFromValue(1.0f);
        fadeOutTransition.setToValue(0.0f);
        fadeOutTransition.setAutoReverse(true);
        
        fadeInTransition = new FadeTransition(Duration.millis(500), details);
        fadeInTransition.setFromValue(0.0f);
        fadeInTransition.setToValue(1.0f);
        fadeInTransition.setAutoReverse(true);
		
	}
	

	private void configureTabs(){
		if(customerTabs.getSelectionModel().getSelectedItem() != null){
			System.out.println("tab: " + customerTabs.getSelectionModel().getSelectedItem());
			System.out.println("Invoices Tab is selected!!");
		}
		
		customerTabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
				System.out.println(newTab.getText());
				if(newTab.getText().equals("Customer Invoices")){
									
					 newCustomer.setDisable(true);
					 deleteCustomer.setDisable(true);
					 saveCustomer.setDisable(true);
					 //customerSelected
					 //printButton.setDisable(true);
					 //updateInvoiceButton.setDisable(true);
					 //deleteInvoiceButton.setDisable(true);
					 
				}else{
					configureButtons();
					if(table != null){
						//table.getSelectionModel().select(getSelectedCustomer());
						updateDeleteCustomerButtonState();
					}
				}

			}	
		});
		
	}
	private void configureButtons() {
		
        if (newCustomer != null) {
        	newCustomer.setDisable(false);
        }
        if (deleteCustomer != null) {
        	deleteCustomer.setDisable(true);
        }
        if (saveCustomer != null) {
        	saveCustomer.setDisable(true);
        }
        if (printButton != null) {
        	printButton.setDisable(true);
        }
    }
        
    // Connect to the model, get the project's names list, and listen to
    // its changes. Initializes the list widget with retrieved project names.
    public ObservableList<Customer> getCustomerList(){
    	System.out.println("getCustomerList called...");
//        filteredData.addAll(service.getCustomers());
    	tableContent = FXCollections.observableList((List<Customer>) service.getCustomers());   	   	
        return tableContent;
   }
    
    
    //@FXML CopyOfInvoicesController invoicesContentTab;
    private final ChangeListener<Customer> customerChangeListener(){
    	ChangeListener<Customer> listener = new ChangeListener<Customer>() {

			@Override
			public void changed(ObservableValue<? extends Customer> observable,
					Customer oldValue, Customer newValue) {
				
				//updateFilteredData();
				updateDetails();
				//updateInvoicesTable();
				//Context.getInstance().setCustomer(newValue);
				updateSaveCustomerButtonState();
				
				updateDeleteCustomerButtonState();
				
//				invoicesContentTab.customerSelected(newValue);
				
				//CopyOfInvoicesController invoicesContentTab2 = new CopyOfInvoicesController();
				//invoicesContentTab2.setText(newValue.getName());
				//invoicesContentTab.getChildren().add(invoicesContentTab2);
				/*
				Parent root = null;
				try {
					root = FXMLLoader.load(this.getClass().getResource("CopyofInvoices.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TextField lblData = (TextField) root.lookup("IIdDetailsText");
				if (lblData!=null) lblData.setText("Hello"); 
				*/
				
				/*
				FXMLLoader fxmlLoader = new FXMLLoader();
				try {
					Parent p = (Parent) fxmlLoader.load(getClass().getResource("CopyofInvoices.fxml").openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CopyOfInvoicesController fooController = (CopyOfInvoicesController) fxmlLoader.getController();
				
				fooController.setText("hello");
				*/
				/*
				Scene scene = table.getScene();
				 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CopyofInvoices.fxml"));
				    Parent root = null;
					try {
						root = (Parent) fxmlLoader.load();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}          

					CopyOfInvoicesController fooController = (CopyOfInvoicesController) fxmlLoader.getController();
*/					
				CopyOfInvoicesController.setText(newValue.getName());				
				
			}
    		
		};
		return listener;
    	
    
    }
    
    protected void updateFilteredData() {
    	filteredData.clear();
        
        for (Customer customer : tableContent) {
            if (matchesFilter(customer)) {
                filteredData.add(customer);
            }
        }
        
        // Must re-sort table after items changed
        reapplyTableSortOrder();
		
	}


	private boolean matchesFilter(Customer customer) {
		 String filterString = filterField.getText();
	      if (filterString == null || filterString.isEmpty()) {
	          // No filter --> Add all.
	          return true;
	      }
	      
	      String lowerCaseFilterString = filterString.toLowerCase();
	      
	      if (customer.getName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
	          return true;
	      } else if (customer.getName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
	          return true;
	      }
	      
	      return false; // Does not match
	}
	
	private void reapplyTableSortOrder() {
		ArrayList<TableColumn<Customer, ?>> sortOrder = new ArrayList<>(table.getSortOrder());
		table.getSortOrder().clear();
		table.getSortOrder().addAll(sortOrder);
	}




    

    private WDPackage selectedPackage;
    
    public void updateDetails(){
    	
    	System.out.println(" updateDetails called ");
    	final Customer customer = getSelectedCustomer();
    	
        if (details != null && customer != null) {
        	
            details.setVisible(customer != null);
    		cid.setText(String.valueOf(customer.getCid()));
    		cnic.setText(customer.getCnic());
        	name.setText(customer.getName());
        	fathername.setText(customer.getFathername());
        	username.setText(customer.getUsername());
        	password.setText(customer.getPassword());
        	zone.setText(customer.getZone());
        	dor.setText(customer.getDor());
        	telephone.setText(customer.getTelephone());
        	mobile.setText(customer.getMobile());
        	email.setText(customer.getEmail());
        	address.setText(customer.getAddress());
        	
        	List<Integer> packageDetails = new ArrayList<Integer>();
        	Set<WDPackage> packSet = customer.getWdpackages();
        	

        	System.out.println("packages " + packSet);
 
        	if(packSet.isEmpty()){
        		packSet = new HashSet<WDPackage>(packageService.getPackages()); 
        	}
    		
        	Iterator<WDPackage> it=packSet.iterator();
        	
        	/**
        	 * Currently only one package is selected and showed in printout
        	 * this needs to be changed.
        	 */
        	
        	selectedPackage = packSet.iterator().next();
        	
            while(it.hasNext())
            {
            	WDPackage value=(WDPackage)it.next();

              System.out.println("package :"+value.getPackageDetails());
            }
        	
    		for(WDPackage p: packSet){
    			System.out.println("package ID: " + p.getPid() + " package details : " + p.getPackageDetails());
    			packageDetails.add(p.getPackageDetails());
    		}
        	
        	
        	/*
        	 * Example with ArrayList: Currently not working
        	 * 
        	
        	List<Integer> packageDetails = new ArrayList<Integer>();
        	List<WDPackage> packages = (List<WDPackage>) customer.getWdpackages();
        	
        	
        	if(packages.isEmpty()) {
        		
        		System.out.println("pacages are null therefore calling service");
        		packages = packageService.getPackages();         
        	}
        	
        	for(WDPackage p: packages){
    			packageDetails.add(p.getPackageDetails());
    		}
    		
    		*/
        	
        	/*
        	 * Example with SortedTree
        	 * 
        	SortedSet<WDPackage> packSet = customer.getWdpackages();
        	

        	System.out.println("packages " + packSet);
 
        	if(packSet.isEmpty()){
        		packSet = new TreeSet<WDPackage>(packageService.getPackages()); 
        	}
    		
        	Iterator<WDPackage> it=packSet.iterator();
        	
            while(it.hasNext())
            {
            	WDPackage value=(WDPackage)it.next();

              System.out.println("package :"+value.getPackageDetails());
            }
        	
    		for(WDPackage p: packSet){
    			System.out.println("package ID: " + p.getPid() + " package details : " + p.getPackageDetails());
    			packageDetails.add(p.getPackageDetails());
    		}
    	
    	    */
        	
        	
        	/*
        	 * Example with LinkedHashSet
        	 * 
        	LinkedHashSet<WDPackage> packSet = customer.getWdpackages();
        	

        	System.out.println("packages " + packSet);
 
        	if(packSet.isEmpty()){
        		packSet = new LinkedHashSet<WDPackage>(packageService.getPackages()); 
        	}
    		
        	Iterator<WDPackage> it=packSet.iterator();
        	
            while(it.hasNext())
            {
            	WDPackage value=(WDPackage)it.next();

              System.out.println("package :"+value.getPackageDetails());
            }
        	
    		for(WDPackage p: packSet){
    			System.out.println("package ID: " + p.getPid() + " package details : " + p.getPackageDetails());
    			packageDetails.add(p.getPackageDetails());
    		}
        	*/
        	
    	
    		packagesContent = FXCollections.observableList((List<Integer>) packageDetails);
    	
    		packagesList.setItems(packagesContent);
    		packagesList.getSelectionModel().selectFirst();
        	
        	
        } else {
    		cid.setText("");
    		cnic.setText("");
        	name.setText("");
        	fathername.setText("");
        	username.setText("");
        	password.setText("");
        	zone.setText("");
        	dor.setText("");
        	telephone.setText("");
        	mobile.setText("");
        	email.setText("");
        	address.setText("");
        	packagesList.getSelectionModel().clearSelection();
        	
        }
        
    	
    }
    
    public Customer getSelectedCustomer() {
        if (table != null) {
            List<Customer> selectedCustomers = table.getSelectionModel().getSelectedItems();
            if (selectedCustomers.size() == 1) {
                final Customer selectedCustomer = selectedCustomers.get(0);
                System.out.println("selectedCustomer : " + selectedCustomer);
                return selectedCustomer;
            }
        }
        return null;
    }
    

    public void newCustomerFired(ActionEvent event) {
    	System.out.println("newCustomerFired Called()....");
    	Customer newCustomer = new Customer();
    	newCustomer.setName("Enter Name");
    	newCustomer.setFathername("Enter Fathername");    	
    	
        if (table != null) {
            // Select the newly created issue.
        	System.out.println(" table is not null ");
            table.getSelectionModel().clearSelection();
            tableContent.add(newCustomer);
            //table.getSelectionModel().
            table.getSelectionModel().select(newCustomer);
        }
        updateDetails();
        updateDeleteCustomerButtonState();
        updateSaveCustomerButtonState();
        MessagesController.displayMessage("New Customer created. Please complete details and Save it");
    }


    public void deleteCustomerFired(ActionEvent event) {
    	System.out.println("deleteCustomerFired Called()....");
    	Customer deleteCustomer = getSelectedCustomer();
    	
    	String deleteResult;
    	try{
    		deleteResult = service.deleteCustomer(deleteCustomer);
    		if(deleteResult.equals("FAILED")){
    			throw new CustomerDeleteException(deleteResult);
    		}else{
    			MessagesController.displayMessage("Customer deleted Successfully");
    			refreshAfterDelete();
    		}
    	}catch (CustomerDeleteException e){
    		System.out.println(e);
    	}
    }


    private void refreshAfterDelete() {
		table.getSelectionModel().clearSelection();
		table.setItems(getCustomerList());
		updateDetails();
		updateDeleteCustomerButtonState();
	}

	public void saveCustomerFired(ActionEvent event) {
    	System.out.println("saveCustomerFired Called()....");
    	Customer updateCustomer = getSelectedCustomer();
    	
    	updateCustomer.setName(name.getText());
    	updateCustomer.setCnic(cnic.getText());
    	updateCustomer.setFathername(fathername.getText());
    	updateCustomer.setUsername(username.getText());
    	updateCustomer.setPassword(password.getText());
    	updateCustomer.setZone(zone.getText());
    	updateCustomer.setDor(dor.getText());
    	updateCustomer.setTelephone(telephone.getText());
    	updateCustomer.setMobile(mobile.getText());
    	updateCustomer.setAddress(address.getText());
    	
    	WDPackage p = packageService.getPackageByDetails((Integer) packagesList.getSelectionModel().getSelectedItem());
//    	List<WDPackage> list = new ArrayList<WDPackage>();
//    	list.add(p);
    	
    	/*
    	LinkedHashSet<WDPackage> set = new LinkedHashSet<WDPackage>();
    	updateCustomer.setWdpackages(set);
    	*/
    	
    	/*
    	List<WDPackage> list = new ArrayList<WDPackage>();
    	updateCustomer.setWdpackages(list);
    	*/
    	
    	Set<WDPackage> set = new HashSet<WDPackage>();
    	updateCustomer.setWdpackages(set);
    	
    	System.out.println("Updating customer:-" + updateCustomer.getCid() + " " + updateCustomer.getName());
    	
    	String updateResult;
    	try{
    		updateResult = service.updateCustomer(updateCustomer);
    		if(updateResult.equals("FAILED")){
    			throw new CustomerUpdateException(updateResult);
    		}else{
    			MessagesController.displayMessage("Customer updated Successfully");
    			refreshAfterSave();
    		}
    	}catch (CustomerUpdateException e){
    		System.out.println(e);
    		//displayMessage("Exception " + e);
    	}
    	
    }
	
	private void refreshAfterSave() {
		System.out.println("refreshAfterSave called...");
    	int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
    	
    	System.out.println(selectedRowIndex);
    	
    	table.getItems().clear();
    	table.setItems(getCustomerList());
    	
    	
    	table.getSelectionModel().select(selectedRowIndex);
    	
    	System.out.println("selectedRowIndex:-" + table.getSelectionModel().getSelectedItem().getName());
    	System.out.println(" refreshAfterSave : "+ getSelectedCustomer().getName());
    	
    	//updateDetails();
    	//resetFields();
    	updateSaveCustomerButtonState();
		
	}


	public void updateSaveCustomerButtonState(){
        boolean disable = true;
        if (saveCustomer != null && table != null) {
            final boolean nothingSelected = table.getSelectionModel().getSelectedItems().isEmpty();
            System.out.println("nothingselected : " +  nothingSelected);
            disable = nothingSelected;
        }
        
        if (disable == false) {
        	
        	System.out.println("disable is false...");
        	final Customer selectedCustomer = getSelectedCustomer();
        	
        	
        	//System.out.println("selectedCustomer :" + selectedCustomer.getName());
        	
        	final DetailsData d = new DetailsData();
        	
        	Customer formCustomer = getFormCustomer();
        	
        	System.out.println("formCustomer :" + formCustomer.getName());

        	
        	SaveState s = computeSaveState(d , selectedCustomer);
        	System.out.println("SaveState is.." + s);
        	
            disable = s != SaveState.UNSAVED;
            
            System.out.println("disable is.." + disable);
        }

        if (saveCustomer != null) {
        	System.out.println("saveCustomer.." + saveCustomer);
        	saveCustomer.setDisable(disable);
        }
    }

	
	private SaveState computeInvoiceSaveState(InvoiceToCust invoice1, InvoiceToCust invoice2) {
        try {
            // These fields are not editable - so if they differ they are invalid
            // and we cannot save.
        	/*
            if (!equal(edited.getCid(), selectedCustomer.getCid())) {
                return SaveState.INVALID;
            }
            */
            // If these fields differ, the issue needs saving.
        	
        	
            if (!equal(invoice1.getStatus(), invoice2.getStatus())) {
            	System.out.println(invoice1.getStatus() + " compare " + invoice2.getStatus());
                return SaveState.UNSAVED;
            }
            
            if (!equal(invoice1.getPaidon(), invoice2.getPaidon())) {
            	System.out.println(invoice1.getPaidon() + " compare " + invoice2.getPaidon());
                return SaveState.UNSAVED;
            }
            
            if (!equal(invoice1.getPaidamount(), invoice2.getPaidamount())) {
            	System.out.println(invoice1.getPaidamount() + " compare " + invoice2.getPaidamount());
                return SaveState.UNSAVED;
            }
            
            
        } catch (Exception x) {
            // If there's an exception, some fields are invalid.
            return SaveState.INVALID;
        }
        // No field is invalid, no field needs saving.
        return SaveState.UNCHANGED;
	}


	private InvoiceToCust getFormInvoice() throws ParseException {
		InvoiceToCust invoice = new InvoiceToCust();
		invoice.setStatus(IStatusDetailsText.getText());
		invoice.setPaidon(IPaidOnDetailsText.getText());
		invoice.setPaidamount(Integer.parseInt(IAmountDetailsText.getText()));	    
		return invoice;
	}


	private Customer getFormCustomer() {
		Customer updateCustomer = new Customer();
		updateCustomer.setName(name.getText());
    	updateCustomer.setFathername(fathername.getText());
    	updateCustomer.setUsername(username.getText());
    	updateCustomer.setPassword(password.getText());
    	updateCustomer.setZone(zone.getText());
    	updateCustomer.setDor(dor.getText());
    	updateCustomer.setTelephone(telephone.getText());
    	updateCustomer.setMobile(mobile.getText());
    	updateCustomer.setAddress(address.getText());
		
		return updateCustomer;
	}

	public void resetFields(){
		name.setText("");
		fathername.setText("");
		username.setText("");
		password.setText("");
		zone.setText("");
		telephone.setText("");
		mobile.setText("");
		address.setText("");
	}
	
    private Customer getCustomerToUpdate() {
    	Customer updateCustomer = new Customer();   	
    	updateCustomer.setCid(Integer.parseInt(cid.getText()));
    	updateCustomer.setCnic(cnic.getText());
    	updateCustomer.setName(name.getText());
    	updateCustomer.setFathername(fathername.getText());
    	updateCustomer.setUsername(username.getText());
    	updateCustomer.setPassword(password.getText());
    	updateCustomer.setZone(zone.getText());
    	updateCustomer.setTelephone(telephone.getText());
    	updateCustomer.setMobile(mobile.getText());
    	updateCustomer.setAddress(address.getText());
		return updateCustomer;
	}

	private static enum SaveState {
        INVALID, UNSAVED, UNCHANGED
    }
	
	private SaveState computeSaveState(ICustomer edited, ICustomer selectedCustomer) {
		/*
		System.out.println(edited.getCid() + " " + edited.getName() + " " + edited.getFathername() + " "
				+ edited.getUsername() +" " + edited.getPassword() + edited.getTelephone() + " " +
				edited.getMobile() + " " + edited.getZone() + " " + edited.getAddress());
		
		System.out.println(selectedCustomer.getName() + " " + selectedCustomer.getFathername() + " "
				+ selectedCustomer.getUsername() +" " + selectedCustomer.getPassword() + selectedCustomer.getTelephone() + " " +
				selectedCustomer.getMobile() + " " + selectedCustomer.getZone() + " " + selectedCustomer.getAddress());
		*/
        try {
            // These fields are not editable - so if they differ they are invalid
            // and we cannot save.
        	/*
            if (!equal(edited.getCid(), selectedCustomer.getCid())) {
                return SaveState.INVALID;
            }
            */
            // If these fields differ, the issue needs saving.
        	
            if (selectedCustomer.getCid() == null) {
                return SaveState.UNSAVED;
            }
        	
            if (!equal(edited.getCnic(), selectedCustomer.getCnic())) {
                return SaveState.UNSAVED;
            }
            
            if (!equal(edited.getName(), selectedCustomer.getName())) {
                return SaveState.UNSAVED;
            }
            if (!equal(edited.getFathername(), selectedCustomer.getFathername())) {
                return SaveState.UNSAVED;
            }
            
            if (!equal(edited.getUsername(), selectedCustomer.getUsername())) {
                return SaveState.UNSAVED;
            }
            if (!equal(edited.getPassword(), selectedCustomer.getPassword())) {
                return SaveState.UNSAVED;
            }
            if (!equal(edited.getZone(), selectedCustomer.getZone())) {
                return SaveState.UNSAVED;
            }
            if (!equal(edited.getDor(), selectedCustomer.getDor())) {
                return SaveState.UNSAVED;
            }
            if (!equal(edited.getTelephone(), selectedCustomer.getTelephone())) {
                return SaveState.UNSAVED;
            }
            
            if (!equal(edited.getMobile(), selectedCustomer.getMobile())) {
                return SaveState.UNSAVED;
            }
            
            if (!equal(edited.getAddress(), selectedCustomer.getAddress())) {
                return SaveState.UNSAVED;
            }
            
        } catch (Exception x) {
            // If there's an exception, some fields are invalid.
            return SaveState.INVALID;
        }
        // No field is invalid, no field needs saving.
        return SaveState.UNCHANGED;
	}
	
    private boolean isVoid(Object o) {
        if (o instanceof String) {
            return isEmpty((String) o);
        } else {
            return o == null;
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }   
	
    private boolean equal(Object o1, Object o2) {
        if (isVoid(o1)) {
            return isVoid(o2);
        }
        return o1.equals(o2);
    }

	private final class DetailsData implements ICustomer {
		
		@Override
		public Integer getCid() {
            if (cid == null || isEmpty(cid.getText())) {
                return null;
            }
            return Integer.parseInt(cid.getText());
		}

            @Override
            public Integer getCkey() {
            if (ckey == null || isEmpty(ckey.getText())) {
                return 0;
             }                
                return Integer.parseInt(ckey.getText());
            }
                
		@Override
		public String getCnic() {
            if (cnic == null || isEmpty(cnic.getText())) {
                return "";
            }
            System.out.println("DetailsData. cnic" + cnic.getText());
            return cnic.getText();
		}
		
		@Override
		public String getName() {
            if (name == null || isEmpty(name.getText())) {
                return "";
            }
            System.out.println("DetailsData. getName" + name.getText());
            return name.getText();
		}

		@Override
		public String getFathername() {
            if (fathername == null || isEmpty(fathername.getText())) {
                return "";
            }
            return fathername.getText();
		}

		@Override
		public String getUsername() {
            if (username == null || isEmpty(username.getText())) {
                return "";
            }
            return username.getText();
		}
		
		@Override
		public String getPassword() {
            if (password == null || isEmpty(password.getText())) {
                return "";
            }
            return password.getText();
		}
		
		@Override
		public String getZone() {
            if (zone == null || isEmpty(zone.getText())) {
                return "";
            }
            return zone.getText();
		}
		
		@Override
		public String getDor() {
            if (dor == null || isEmpty(dor.getText())) {
                return "";
            }
            return dor.getText();
		}		
		
		@Override
		public String getTelephone() {
            if (telephone == null || isEmpty(telephone.getText())) {
                return "";
            }
            return telephone.getText();
		}
		
		@Override
		public String getMobile() {
            if (mobile == null || isEmpty(mobile.getText())) {
                return "";
            }
            return mobile.getText();
		}		

		@Override
		public String getEmail() {
            if (email == null || isEmpty(email.getText())) {
                return "";
            }
            return email.getText();
		}
		
		@Override
		public String getAddress() {
            if (address == null || isEmpty(address.getText())) {
                return "";
            }
            return address.getText();
		}

        @Override
        public String getCustomerPackage() {
            if (customerPackage == null || isEmpty(customerPackage.getText())) {
                return "";
            }
            return customerPackage.getText();
        }                
                
		@Override
		public String getComments() {
            if (comments == null || isEmpty(comments.getText())) {
                return "";
            }
            return comments.getText();
		}                
				
	}
    
    private void configureCustomerDetails() {
        if (details != null) {
            details.setVisible(false);
        }

        if (details != null) {
            details.addEventFilter(EventType.ROOT, new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    if (event.getEventType() == MouseEvent.MOUSE_RELEASED
                            || event.getEventType() == KeyEvent.KEY_RELEASED) {
        				//updateDetails();
        				updateSaveCustomerButtonState();
                    }
                }
            });
        }
    }
    

    
    
    private void updateDeleteCustomerButtonState() {
    	System.out.println(" updateDeleteCustomerButtonState called....");
        boolean disable = true;
        if (deleteCustomer != null && table != null) {
            final boolean nothingSelected = table.getSelectionModel().getSelectedItems().isEmpty();
            disable = nothingSelected;
            System.out.println(" nothingSelected is..." + nothingSelected);
        }
 /*       
        Integer cid = null;
        
        if(! table.getSelectionModel().getSelectedItems().isEmpty()){
           cid = getSelectedCustomer().getCid();
           System.out.println(" cid " + cid);
        }
        
        if(cid == null){
        	System.out.println("getSelectedCustomer is not null " + getSelectedCustomer());
        	disable = true;
        }
 */       
        if(customerTabs.getSelectionModel().getSelectedItem().getText().equals("Customer Invoices")){
        	disable = true;
        }
        
        if (deleteCustomer != null) {
        	deleteCustomer.setDisable(disable);
        }
    }
    
 
    

    


	protected void updateInvoiceDetails(InvoiceToCust newValue) {
		if(newValue != null){
			printButton.setDisable(false);
			deleteInvoiceButton.setDisable(false);
			IIdDetailsText.setText(newValue.getIntocustid().toString());
			IPaidOnDetailsText.setText(newValue.getPaidon().toString());
			IStatusDetailsText.setText(newValue.getStatus());
			IAmountDetailsText.setText(newValue.getPaidamount().toString());
		}
		else {
			printButton.setDisable(true);
			deleteInvoiceButton.setDisable(true);
			IIdDetailsText.setText("");
			IPaidOnDetailsText.setText("");
		    IStatusDetailsText.setText("");
		    IAmountDetailsText.setText("");	
		}
	}

	
	private TableColumn<InvoiceToCust, Product> createCustumFactory(TableColumn<InvoiceToCust, Product> colName, final String methodName){
    	
    	colName.setCellValueFactory(new PropertyValueFactory<InvoiceToCust, Product>("product"));
    	colName.setCellFactory(new Callback<TableColumn<InvoiceToCust, Product>,TableCell<InvoiceToCust, Product>>(){
            @Override
            public TableCell<InvoiceToCust, Product> call(TableColumn<InvoiceToCust, Product> param) {                
                final TableCell<InvoiceToCust, Product> cell = new TableCell<InvoiceToCust, Product>(){
                    @Override
                    public void updateItem(Product item, boolean empty) {                        
                        if(item!=null){ 
                        	switch (methodName){
                        	case "Month":
                        		setText(item.getMonth());
                        		break; 
                        	case "IssueDate":
                        		setText(item.getIssuedate().toString());
                        		break;                        	
                        	case "DueDate":
                        		setText(item.getDuedate().toString());
                        		break;                       		
                        	case "Sum":
                        		setText(item.getPayment().toString());
                        		break;
                        	case "Fine":
                        		setText(item.getFine().toString());
                        		break;	
                        	}
                        }
                    }
                };             
                return cell;
            }
    	});
		return colName;
    }
    
	public void printInvoice(ActionEvent event){
		System.out.println("Print Invoice has been fired..");
		/*InvoicePrinter printer = new InvoicePrinter();
		printer.toPrint(getSelectedCustomer().getInvoices());
		printer.startPrint();
		*/
		
		/*
		FXPrintable printer = new FXPrintable();
		printer.startPrint();
		*/
		/*
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	FXPrintable frame = new FXPrintable();
                    frame.init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });		
		*/
		/*
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	FXPrintable2 frame = new FXPrintable2();
                    frame.startPrint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
		*/
		
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	Customer cus = getSelectedCustomer();
               // new Printer(cus, selectedInvoice, selectedPackage).setVisible(true);
            }
        });	
	}



    /*
	public void newInvoice(ActionEvent event){
		System.out.println("New Invoice has been fired....");
    	Customer customer = getSelectedCustomer();
    	Product product = pService.getProductByMonth("FEBRUARY");  
    	List<InvoiceToCust> customerOldInvoices = customer.getInvoices();
    	boolean alreadyPaid = false;
    	if(customerOldInvoices.size() > 0){	
    	 for(InvoiceToCust i: customerOldInvoices){
    		 if(i.getProduct().equals(product)){
    			 System.out.println("Month already paid...");
    			 displayMessage("Invoice already created for this month");
    			 alreadyPaid = true;
    		 }
    	 }
    	}
    	if(alreadyPaid == false){ 
        	InvoiceToCust newInvoice = new InvoiceToCust();
        	newInvoice.setPaidamount(699);
        	newInvoice.setStatus("UNPAID");
        	try {
    			newInvoice.setPaidon("10-03-2013");
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	newInvoice.setProduct(product);	
    	customer.getInvoices().add(newInvoice);
    	service.updateCustomer(customer);
    	displayMessage("Invoice created successfully");
    	updateInvoicesTable();
    	}

	}
    */


	

	
    /*
     * To create Dummy data 
     * 
     */
    
    /*
	private ObservableList<ObservableCustomerImpl> getTableContents() {
    	System.out.println("getTableContents() called...");
    	ObservableCustomerImpl c1 = new ObservableCustomerImpl("1","abid" );
    	ObservableCustomerImpl c2 = new ObservableCustomerImpl("2","abid2" );
    	ObservableCustomerImpl c3 = new ObservableCustomerImpl("3","abid3" );
    	ObservableCustomerImpl c4 = new ObservableCustomerImpl("4","abid4" );
    	ObservableCustomerImpl c5 = new ObservableCustomerImpl("5","abid5" );
    	tableContent.add(c1);
    	tableContent.add(c2);
    	tableContent.add(c3);
    	tableContent.add(c4);
    	tableContent.add(c5);
    	
    	
    	for(ObservableCustomerImpl c: tableContent){
    		System.out.println(" Customer : " + c.getName() + " " + c.getFathername());
    		
    	}
    	
		return tableContent;
	}
*/

}
