package pk.webdoor.fx.controllers;

import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import pk.webdoor.fx.exceptions.CustomerUpdateException;
import pk.webdoor.fx.service.CustomerService;
import pk.webdoor.fx.service.ProductService;
import pk.webdoor.model.Customer;
import pk.webdoor.model.InvoiceToCust;
import pk.webdoor.model.Product;

//public class CopyOfInvoicesController implements Initializable, ChangeListener<Customer> {
public class CopyOfInvoicesController implements Initializable{

   
    @FXML public TableView<InvoiceToCust> invoicesTable;
    @FXML GridPane invoiceDetails;
    
    @FXML private TableColumn<InvoiceToCust, Integer> invoiceID;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceMonth;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceIssueDate;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceDueDate;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceSum;
    @FXML private TableColumn<InvoiceToCust, Product> invoiceAfterDue;
    
    @FXML static TextField IIdDetailsText;
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
    

    
    private final ObservableList<InvoiceToCust> invoicesContent = FXCollections.observableArrayList();
//    private ObservableList<InvoiceToCust> invoicesContent;
    
    private ObservableList<InvoiceToCust> displayedInvoices = FXCollections.observableArrayList();
//    private ObservableList<InvoiceToCust> displayedInvoices;
    
	private FadeTransition fadeOutTransition;

	private FadeTransition fadeInTransition;
	
	//private SingleSelectionModel<Tab> tabsSelection = customerTabs.getSelectionModel();
	
	private static ContollerListener listener;
	
    public void addListener(ContollerListener listener){
    	CopyOfInvoicesController.listener = listener;
    	System.out.println("Listern added:- " + listener);
    }   


	public CopyOfInvoicesController(){
	     
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
		 assert invoicesTable != null : "fx:id=\"invoicesTable\" was not injected: check your FXML file 'IssueTrackingLite.fxml'.";
	       configureInvoiceDetails();
	        
	       configureInvoiceTable();
	       configureProductsTable();
	       //printButton.setDisable(true);
		   //updateInvoiceButton.setDisable(true);
		   //deleteInvoiceButton.setDisable(true);
		
	}
	
	public static DataFormat dataFormat =  new DataFormat("mycell2");
	private Product dragProduct = null;

	private void configureProductsTable() {
		
		pID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("prodid"));
		pMonth.setCellValueFactory(new PropertyValueFactory<Product, String>("month"));
		pIssueDate.setCellValueFactory(new PropertyValueFactory<Product, String>("issuedate"));
		pDueDate.setCellValueFactory(new PropertyValueFactory<Product, String>("duedate"));
		pPayment.setCellValueFactory(new PropertyValueFactory<Product, Integer>("payment"));
		pFine.setCellValueFactory(new PropertyValueFactory<Product, Integer>("fine"));
		
		pID.setPrefWidth(75);
		pMonth.setPrefWidth(75);
		pIssueDate.setPrefWidth(75);
		pDueDate.setPrefWidth(75);
		pPayment.setPrefWidth(75);
		pFine.setPrefWidth(75);  
		
		productsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		productsTableContents = FXCollections.observableList((List<Product>) pService.getAllProducts()); 
		
		productsTable.setItems(productsTableContents);
		
		productsTable.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override public void handle(final MouseEvent me) {
				final Dragboard db = productsTable.startDragAndDrop(TransferMode.COPY);
				final ClipboardContent content = new ClipboardContent();
				Object dragItem = productsTable.getSelectionModel().getSelectedItem();
				dragProduct = (Product) dragItem;
				content.put(dataFormat, dragItem.toString());
				db.setContent(content);
				me.consume();
			}
		});
				

	}



    

	protected void updateInvoicesTable() {
    	System.out.println(" updateInvoicesTable called ");
    	final Customer customer = getSelectedCustomer();
    	if(customer != null){
    	  invoicesTable.getSelectionModel().clearSelection();	
    	  invoicesTable.getItems().clear();
//        Customer cus = service.getCustomers().get(0);
    		System.out.println(" customer..... " + customer.getCid());
//        System.out.println(customer.getCid() + " " + customer.getName() + " " + customer.getInvoices());
       
        List <InvoiceToCust> invoices = customer.getInvoices();
        
        //System.out.println(invoices.size() + " " + invoices );
        
    	/*
        for(InvoiceToCust i: invoices){
        System.out.println(i.getIntocustid() + " " + i.getProduct().getMonth()+ " " + i.getProduct().getIssuedate()
        		+ " " + i.getProduct().getDuedate() + " " + i.getProduct().getPayment());
        }
        */
    	//invoicesContent = FXCollections.observableList((List<InvoiceToCust>) invoices); 
        //invoicesTable.getItems().addAll(invoices);
         if(invoicesContent.isEmpty()){
        	 System.out.println("invoicesTable is  null");
        	 invoicesContent.addAll(invoices);
        	 invoicesTable.setItems(invoicesContent);
         }else {
        	 System.out.println(" invoicesTable is not null");
         }
    	}
    	updateUpdateInvoiceButtonState();
    	
		
	}


	
	private void updateUpdateInvoiceButtonState(){
		boolean disable = true;
        if (updateInvoiceButton != null && invoicesTable != null) {
            final boolean nothingSelected = invoicesTable.getSelectionModel().getSelectedItems().isEmpty();
            System.out.println("nothingselected : " +  nothingSelected);
            disable = nothingSelected;
        }
        
        if (disable == false && selectedInvoice != null) {
        	
        	System.out.println("disable is false...");
        	
        	InvoiceToCust formInvoice = null;
			try {
				formInvoice = getFormInvoice();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	System.out.println("formInvoice paid Date:" + formInvoice.getPaidamount());

        	SaveState s = computeInvoiceSaveState(formInvoice , selectedInvoice);
        	System.out.println("SaveState is.." + s);
        	
            disable = s != SaveState.UNSAVED;
            
            System.out.println("disable is.." + disable);
        }
        
        if (updateInvoiceButton != null) {
        	updateInvoiceButton.setDisable(disable);
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
	private InvoiceToCust getFormInvoice() throws ParseException {
		InvoiceToCust invoice = new InvoiceToCust();
		invoice.setStatus(IStatusDetailsText.getText());
		invoice.setPaidon(IPaidOnDetailsText.getText());
		invoice.setPaidamount(Integer.parseInt(IAmountDetailsText.getText()));	    
		return invoice;
	}



	private static enum SaveState {
        INVALID, UNSAVED, UNCHANGED
    }
	

    
    private void configureInvoiceDetails() {
        /*
    	if (invoiceDetails != null) {
        	invoiceDetails.setVisible(false);
        }*/

        if (invoiceDetails != null) {
        	invoiceDetails.addEventFilter(EventType.ROOT, new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    if (event.getEventType() == MouseEvent.MOUSE_RELEASED
                            || event.getEventType() == KeyEvent.KEY_RELEASED) {
        				//updateDetails();
        				//updateUpdateInvoiceButtonState();
                    }
                }
            });
        }
    }
    

    
    private void configureInvoiceTable() {
    	invoiceID.setCellValueFactory(new PropertyValueFactory<InvoiceToCust, Integer>("intocustid"));
    	
    	invoiceMonth = createCustumFactory(invoiceMonth, "Month");  
    	invoiceIssueDate = createCustumFactory(invoiceIssueDate, "IssueDate");   
    	invoiceDueDate = createCustumFactory(invoiceDueDate, "DueDate"); 
    	invoiceSum = createCustumFactory(invoiceSum, "Sum");    	
    	invoiceAfterDue = createCustumFactory(invoiceAfterDue, "Fine");
    	
    	invoiceID.setPrefWidth(75);
    	invoiceMonth.setPrefWidth(75);
    	invoiceIssueDate.setPrefWidth(75);
    	invoiceDueDate.setPrefWidth(75);
    	invoiceSum.setPrefWidth(75);
    	invoiceAfterDue.setPrefWidth(75);  	
        
        invoicesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 /*       
        Customer cus = service.getCustomers().get(0);
        System.out.println(cus.getCid() + " " + cus.getName() + " " + cus.getInvoices());
       
        List <InvoiceToCust> invoices = cus.getInvoices();
        
        System.out.println(invoices.size() + " " + invoices );
        
    	
        for(InvoiceToCust i: invoices){
        System.out.println(i.getIntocustid() + " " + i.getProduct().getMonth()+ " " + i.getProduct().getIssuedate()
        		+ " " + i.getProduct().getDuedate() + " " + i.getProduct().getPayment());
        }
        
        invoicesContent = FXCollections.observableList((List<InvoiceToCust>) getSelectedCustomer().getInvoices()); 
*/    	   
 //       displayedInvoices = FXCollections.observableList((List<InvoiceToCust>) service.getCustomers().get(0).getInvoices()); 
    	invoicesTable.setItems(invoicesContent);
    	
    	assert invoicesTable.getItems() == invoicesContent;
    	
    	invoicesTable.setOnDragOver(new EventHandler<DragEvent>() {
			@Override public void handle(final DragEvent de) {
				de.acceptTransferModes(TransferMode.COPY);
				de.consume();
			}
		});
    	
    	invoicesTable.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override public void handle(final DragEvent de) {
				
                if (de.getGestureSource() != invoicesTable &&
                		de.getDragboard().hasString()) {
                    //target.setFill(Color.GREEN);
                }
                
                de.consume();
				
			}
		});
    	
    	invoicesTable.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
            	//invoicesTable.setFill(Color.BLACK);
                event.consume();
            }
        });
    	
    	
    	invoicesTable.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                String cellS = (String)event.getDragboard().getContent(dataFormat);

                Object o = event.getDragboard().getContent(dataFormat);

                //Product p = (Product) o;
                
                boolean success = false;
                if (o != null) {
                	addNewInvoice(dragProduct);
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
            }
        });
    	
    	invoicesTable.getSelectionModel().selectedItemProperty().addListener(invoiceTableChangeListener());
    	
	}
    
	public void addNewInvoice(Product product){
		System.out.println("addNewInvoice has been fired....");
    	Customer customer = getSelectedCustomer();
    	List<InvoiceToCust> customerOldInvoices = customer.getInvoices();
    	boolean alreadyPaid = false;
    	if(customerOldInvoices.size() > 0){	
    	 for(InvoiceToCust i: customerOldInvoices){
    		 if(i.getProduct().getMonth().equals(product.getMonth())){
    			// System.out.println("Month already paid...");
    			 MessagesController.displayMessage("Invoice already created for this month");
    			 alreadyPaid = true;
    		 }
    	 }
    	}
    	if(alreadyPaid == false){ 
        	InvoiceToCust newInvoice = new InvoiceToCust();
        	newInvoice.setPaidamount(000);
        	newInvoice.setStatus("UNPAID");
        	newInvoice.setPaidon("10-03-2013");
        	newInvoice.setProduct(product);	
    	customer.getInvoices().add(newInvoice);
    	service.updateCustomer(customer);
    	MessagesController.displayMessage("Invoice created successfully");
    	updateInvoicesTable();
    	}

	}
    
    private InvoiceToCust selectedInvoice = null;
    
    private final ChangeListener<InvoiceToCust> invoiceTableChangeListener(){
    	ChangeListener<InvoiceToCust> listener = new ChangeListener<InvoiceToCust>() {

			@Override
			public void changed(
					ObservableValue<? extends InvoiceToCust> observable,
					InvoiceToCust oldValue, InvoiceToCust newValue) {
				selectedInvoice = newValue;
				updateInvoiceDetails(newValue);
				updateUpdateInvoiceButtonState();
				
			}
		};
		return listener;
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

	public void updateInvoice(ActionEvent event){
    	System.out.println("saveCustomerFired Called()....");
    	InvoiceToCust invoice = selectedInvoice;
    	
    	Customer customer = getSelectedCustomer();
    	List <InvoiceToCust> invoices = customer.getInvoices();
    	
    	invoices.remove(invoice);
    	
		invoice.setStatus(IStatusDetailsText.getText());
		invoice.setPaidon(IPaidOnDetailsText.getText());
		invoice.setPaidamount(Integer.parseInt(IAmountDetailsText.getText()));	

    	
    	System.out.println("Updating invoice:-" + invoice.getIntocustid());
    	
    	invoices.add(invoice);
    	
    	String updateResult;
    	try{
    		updateResult = service.updateCustomer(customer);
    		if(updateResult.equals("FAILED")){
    			throw new CustomerUpdateException(updateResult);
    		}else{
    			MessagesController.displayMessage("Customer updated Successfully");
    			updateUpdateInvoiceButtonState();
    		}
    	}catch (CustomerUpdateException e){
    		System.out.println(e);
    		//displayMessage("Exception " + e);
    	}
	}
	
	public void deleteInvoice(ActionEvent event){
    	System.out.println("deleteInvoice Called()....");
    	InvoiceToCust invoice = selectedInvoice;
    	
    	Customer customer = getSelectedCustomer();
    	List <InvoiceToCust> invoices = customer.getInvoices();
    	
    	invoices.remove(invoice);
    	
    	String updateResult;
    	try{
    		updateResult = service.updateCustomer(customer);
    		if(updateResult.equals("FAILED")){
    			throw new CustomerUpdateException(updateResult);
    		}else{
    			MessagesController.displayMessage("Customer updated Successfully");
    			updateInvoicesTable();
    		}
    	}catch (CustomerUpdateException e){
    		System.out.println(e);
    		//displayMessage("Exception " + e);
    	}
	}
	

/*	
	@Override
	public void changed(ObservableValue<? extends Customer> observable,
			Customer oldValue, Customer newValue) {
		System.out.println("Customer is changed.."+ newValue.getName());
		selectedCustomer = newValue;
		//updateInvoicesTable();
		customerUnselected(oldValue);
		customerSelected(newValue);
	}
*/
	
    public void customerSelected(Customer newValue) {
    	System.out.println("I am called..");

    	/*
    	invoicesTable.getItems().clear();
    	List <InvoiceToCust> invoices = getSelectedCustomer().getInvoices();
    	displayedInvoices.addAll(invoices);

    	invoicesTable.getItems().addAll(invoices);
    	displayedInvoices.addListener(customerInvoicesListener);
		*/
    	List <InvoiceToCust> invoices = newValue.getInvoices();
    	for(InvoiceToCust i: invoices){
    		//invoicesTable.getItems().add(i);
    		//invoicesTable.setItems(null);
    		displayedInvoices.add(i);
    		System.out.println(i);
    		//IIdDetailsText.setText(i.getPaidon()); 
    	}

//    	displayedInvoices = FXCollections.observableList((List<InvoiceToCust>) invoices); 
    	
//    	invoicesTable.setItems(displayedInvoices);
    		
	}





	private void customerUnselected(Customer oldCustomer) {
        if (oldCustomer != null) {
            displayedInvoices.removeListener(customerInvoicesListener);
            displayedInvoices = null;
            invoicesTable.getSelectionModel().clearSelection();
            invoicesTable.getItems().clear();
            /*
            if (newIssue != null) {
                newIssue.setDisable(true);
            }
            if (deleteIssue != null) {
                deleteIssue.setDisable(true);
            }
            */
        }
		
	}

    private final ListChangeListener<InvoiceToCust> customerInvoicesListener = new ListChangeListener<InvoiceToCust>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends InvoiceToCust> c) {
            if (invoicesTable == null) {
                return;
            }
            while (c.next()) {
                if (c.wasAdded() || c.wasReplaced()) {
                    for (InvoiceToCust p : c.getAddedSubList()) {
                    	invoicesTable.getItems().add(p);
                    }
                }
                if (c.wasRemoved() || c.wasReplaced()) {
                    for (InvoiceToCust p : c.getRemoved()) {
                    	InvoiceToCust removed = null;
                        // Issue already removed:
                        // we can't use model.getIssue(issueId) to get it.
                        // we need to loop over the table content instead.
                        // Then we need to remove it - but outside of the for loop
                        // to avoid ConcurrentModificationExceptions.
                        for (InvoiceToCust t : invoicesTable.getItems()) {
                            if (t.equals(p)) {
                                removed = t;
                                break;
                            }
                        }
                        if (removed != null) {
                        	invoicesTable.getItems().remove(removed);
                        }
                    }
                }
            }
        }
    };


	private Customer selectedCustomer;
	private Customer getSelectedCustomer(){
		return this.selectedCustomer;
	}


	
	public void printInvoice(ActionEvent event){
		System.out.println("Print Invoice has been fired..");
	}	

	@FXML Label IStatusDetailsLabel;
	
    public static void setText(String value) {
    	System.out.println("setText is called..");
        IIdDetailsText.setText(value);
 /*       IPaidOnDetailsText.setText(value);
        IStatusDetailsText.setText(value);
        IAmountDetailsText.setText(value);
        IStatusDetailsLabel.setText(value);
*/    }
}