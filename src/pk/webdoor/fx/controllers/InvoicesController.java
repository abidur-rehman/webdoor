package pk.webdoor.fx.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import pk.webdoor.fx.service.CustomerService;
import pk.webdoor.fx.service.ProductService;
import pk.webdoor.model.Customer;
import pk.webdoor.model.InvoiceToCust;
import pk.webdoor.model.Product;

public class InvoicesController implements Initializable,
		ChangeListener<Customer> {

	@FXML private TableView<InvoiceToCust> invoicesTable1;
	private final ObservableList<InvoiceToCust> invoicesContent = FXCollections.observableArrayList();
	// private ObservableList<InvoiceToCust> invoicesContent;

	@FXML private GridPane invoiceDetails;

	@FXML private TableColumn<InvoiceToCust, Integer> invoiceID;
	@FXML private TableColumn<InvoiceToCust, Product> invoiceMonth;
	@FXML private TableColumn<InvoiceToCust, Product> invoiceIssueDate;
	@FXML private TableColumn<InvoiceToCust, Product> invoiceDueDate;
	@FXML private TableColumn<InvoiceToCust, Product> invoiceSum;
	@FXML private TableColumn<InvoiceToCust, Product> invoiceAfterDue;

	@FXML TextField IIdDetailsText;
	@FXML TextField IPaidOnDetailsText;
	@FXML TextField IStatusDetailsText;
	@FXML TextField IAmountDetailsText;
	@FXML Button printButton;
	@FXML Button updateInvoiceButton;
	@FXML Button deleteInvoiceButton;

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

	private Customer selectedCustomer;

	public InvoicesController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configureInvoiceDetails();

		configureInvoiceTable();
		configureProductsTable();
		printButton.setDisable(true);
		updateInvoiceButton.setDisable(true);
		deleteInvoiceButton.setDisable(true);

	}

	public void printInvoice(ActionEvent event) {
		System.out.println("Print Invoice has been fired..");

	}

	private void configureInvoiceTable() {
		invoiceID.setCellValueFactory(new PropertyValueFactory<InvoiceToCust, Integer>("intocustid"));

		/*
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
		*/

		invoicesTable1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		/*       
		       Customer cus = service.getCustomers().get(0);
		       System.out.println(cus.getCid() + " " + cus.getName() + " " + cus.getInvoices());
		      
		       List <InvoiceToCust> invoices = cus.getInvoices();
		       
		       System.out.println(invoices.size() + " " + invoices );
		       
		   	
		       for(InvoiceToCust i: invoices){
		       System.out.println(i.getIntocustid() + " " + i.getProduct().getMonth()+ " " + i.getProduct().getIssuedate()
		       		+ " " + i.getProduct().getDuedate() + " " + i.getProduct().getPayment());
		       }
		       
		   	invoicesContent = FXCollections.observableList((List<InvoiceToCust>) invoices); 
		*/
		invoicesTable1.setItems(invoicesContent);

		invoicesTable1.getSelectionModel().selectedItemProperty().addListener(invoiceTableChangeListener());

	}

	private TableColumn<InvoiceToCust, Product> createCustumFactory(
			TableColumn<InvoiceToCust, Product> colName, final String methodName) {

		colName.setCellValueFactory(new PropertyValueFactory<InvoiceToCust, Product>("product"));
		colName.setCellFactory(new Callback<TableColumn<InvoiceToCust, Product>, TableCell<InvoiceToCust, Product>>() {
			@Override
			public TableCell<InvoiceToCust, Product> call(
					TableColumn<InvoiceToCust, Product> param) {
				final TableCell<InvoiceToCust, Product> cell = new TableCell<InvoiceToCust, Product>() {
					@Override
					public void updateItem(Product item, boolean empty) {
						if (item != null) {
							switch (methodName) {
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

	}

	public void updateInvoice(ActionEvent event) {
		System.out.println("saveCustomerFired Called()....");

	}

	public void deleteInvoice(ActionEvent event) {
		System.out.println("deleteInvoice Called()....");

	}

	public void addNewInvoice(Product product) {
		System.out.println("addNewInvoice has been fired....");
		Customer customer = getSelectedCustomer();
		List<InvoiceToCust> customerOldInvoices = customer.getInvoices();
		boolean alreadyPaid = false;
		if (customerOldInvoices.size() > 0) {
			for (InvoiceToCust i : customerOldInvoices) {
				if (i.getProduct().getMonth().equals(product.getMonth())) {
					// System.out.println("Month already paid...");
					MessagesController.displayMessage("Invoice already created for this month");
					alreadyPaid = true;
				}
			}
		}
		if (alreadyPaid == false) {
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

	private final ChangeListener<InvoiceToCust> invoiceTableChangeListener() {
		ChangeListener<InvoiceToCust> listener = new ChangeListener<InvoiceToCust>() {

			@Override
			public void changed(
					ObservableValue<? extends InvoiceToCust> observable,
					InvoiceToCust oldValue, InvoiceToCust newValue) {
				selectedInvoice = newValue;
				updateInvoiceDetails(newValue);
				// updateUpdateInvoiceButtonState();

			}
		};
		return listener;
	}

	protected void updateInvoiceDetails(InvoiceToCust newValue) {
		if (newValue != null) {
			printButton.setDisable(false);
			deleteInvoiceButton.setDisable(false);
			IIdDetailsText.setText(newValue.getIntocustid().toString());
			IPaidOnDetailsText.setText(newValue.getPaidon().toString());
			IStatusDetailsText.setText(newValue.getStatus());
			IAmountDetailsText.setText(newValue.getPaidamount().toString());
		} else {
			printButton.setDisable(true);
			deleteInvoiceButton.setDisable(true);
			IIdDetailsText.setText("");
			IPaidOnDetailsText.setText("");
			IStatusDetailsText.setText("");
			IAmountDetailsText.setText("");
		}
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
						// updateDetails();
						// updateUpdateInvoiceButtonState();
					}
				}
			});
		}
	}

	protected void updateInvoicesTable() {
		System.out.println(" updateInvoicesTable called ");
		final Customer customer = getSelectedCustomer();
		if (customer != null) {

			// invoicesTable1.getItems().clear();
			// Customer cus = service.getCustomers().get(0);
			System.out.println(" customer..... " + customer.getCid());
			// System.out.println(customer.getCid() + " " + customer.getName() +
			// " " + customer.getInvoices());

			List<InvoiceToCust> invoices = customer.getInvoices();

			System.out.println(invoices.size() + " " + invoices);

			/*
			for(InvoiceToCust i: invoices){
			System.out.println(i.getIntocustid() + " " + i.getProduct().getMonth()+ " " + i.getProduct().getIssuedate()
					+ " " + i.getProduct().getDuedate() + " " + i.getProduct().getPayment());
			}
			*/
			// invoicesContent =
			// FXCollections.observableList((List<InvoiceToCust>) invoices);
			invoicesTable1.getItems().addAll(invoices);

			// invoicesTable1.setItems(invoicesContent);
		}
		// updateUpdateInvoiceButtonState();

	}

	@Override
	public void changed(ObservableValue<? extends Customer> observable,
			Customer oldValue, Customer newValue) {
		// Context.getInstance().getCustomer();
		System.out.println("Customer is changed.." + newValue.getName());
		selectedCustomer = newValue;
		updateInvoicesTable();

	}

	private Customer getSelectedCustomer() {
		return this.selectedCustomer;
	}

}
