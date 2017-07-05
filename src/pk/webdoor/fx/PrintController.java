package pk.webdoor.fx;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import pk.webdoor.model.Customer;
import pk.webdoor.model.InvoiceToCust;
import pk.webdoor.model.Product;
import pk.webdoor.model.WDPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PrintController implements Initializable{

    @FXML TextField cName;
    @FXML TextField cFName;
    @FXML TextField cAddress;
    @FXML TextField cMobile;
    @FXML TextField cKey;
    @FXML TextField cZone;
    @FXML TextField cPackage;
    @FXML TextField bMonth;
    @FXML TextField iDate;
    @FXML TextField dDate;
    @FXML TextField amount;
    @FXML TextField fine;
    @FXML TextField cKeyRecept;
    @FXML TextField cCusNameRecept;
    @FXML TextField cCusAddRecept;
    @FXML TextField cCusMobRecept;
    @FXML TextField cCusZonRecept;
    @FXML TextField cCusPacRecept;
    @FXML TextField amountRecept;
    @FXML TextField feeRecept;
    @FXML TableView<InvoiceToCust> billHistory;

    @FXML TableColumn<InvoiceToCust, String> historystatus;
    @FXML TableColumn<InvoiceToCust, Integer> historyAmount;
    @FXML TableColumn<InvoiceToCust, Product> historyMonth;
    @FXML TableColumn<InvoiceToCust, String> historyPaidOn;
    
    //private ObservableList<InvoiceToCust> billHistoryContent;
    
    private ObservableList<InvoiceToCust> billHistoryContent = FXCollections.observableArrayList();
   // private final ObservableList<InvoiceToCust> billHistoryContent = FXCollections.observableArrayList();
    
    private Customer cus = Printer.cus;
    
    private InvoiceToCust selectedInvoice = Printer.selectedInvoice;
    
    private WDPackage selectedPackage = Printer.selectedPack; 
    
    private Product pro;
    
    private List<InvoiceToCust> invoices = null;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//configureSelectedCustomer();
		System.out.println("initialize called");
		reset();
		configureCustomerDetails();
		configureBillingMonth();
		configureBillHistory();
		configureCustomerRecept();
		
	}

	private void reset() {
		
	  if(invoices != null){
		  invoices = null;
	  }
	  
	  billHistory.getItems().clear();
	  
	  for(InvoiceToCust i : billHistory.getItems()){
		  System.out.println(i.getPaidon());
		  
	  }
	  
	  if(billHistory.getItems().isEmpty()){
		  System.out.println(" billHistoryContent.isEmpty()");
		  
	  }else{
		  System.out.println("! billHistoryContent.isEmpty()");
		  //billHistoryContent.removeAll(invoices);
	  }
	  
	  
		
	}

	private void configureSelectedCustomer() {
		
		invoices = cus.getInvoices();
		
		billHistory.getItems().clear();
		
		if(! billHistory.getItems().isEmpty()){
			System.out.println("Bill history is not null " + billHistory.getItems().toString());
			billHistory.getItems().clear();
			//billHistoryContent.get(0).getIntocustid();
		}

		
		int size = invoices.size();
		System.out.println("Customer invoices are: " + size );
		for(InvoiceToCust i : invoices){
			System.out.println(i.getIntocustid());
		}
		
		billHistory.getItems().addAll(invoices);
		
	}

	private void configureBillHistory() {
			

		System.out.println("configureBillHistory called...");
		
		historyMonth = createCustumFactory(historyMonth, "Month"); 
		historystatus.setCellValueFactory(new PropertyValueFactory<InvoiceToCust, String>("status"));
		historyAmount.setCellValueFactory(new PropertyValueFactory<InvoiceToCust, Integer>("paidamount"));
		historyPaidOn.setCellValueFactory(new PropertyValueFactory<InvoiceToCust, String>("paidon"));
		
		historyMonth.setPrefWidth(75);
		historystatus.setPrefWidth(75);
		historyAmount.setPrefWidth(75);
		historyPaidOn.setPrefWidth(75);
/*		
		historyMonth.setSortable(false);
		historystatus.setSortable(false);
		historyAmount.setSortable(false);
		historyPaidOn.setSortable(false);
*/		
		billHistory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//		billHistory.getSelectionModel().setCellSelectionEnabled(false);
//		billHistory.getSelectionModel().setCellSelectionEnabled(true);
		
		
		
		//billHistoryContent = FXCollections.observableList((List<InvoiceToCust>) getInvoices());   
		getInvoices();
		
	    //billHistory.setItems(billHistoryContent);
	    
	    //assert billHistory.getItems() == billHistoryContent;
		//billHistory.getSelectionModel().selectedItemProperty().addListener(customerChangeListener());
		
		//billHistoryContent.addListener(projectNamesListener());
		
		//billHistory.getSelectionModel().selectedItemProperty().addListener(invoiceTableChangeListener());

	}
	
    private final ChangeListener<InvoiceToCust> invoiceTableChangeListener(){
    	ChangeListener<InvoiceToCust> listener = new ChangeListener<InvoiceToCust>() {

			@Override
			public void changed(
					ObservableValue<? extends InvoiceToCust> observable,
					InvoiceToCust oldValue, InvoiceToCust newValue) {
			
				System.out.println("List changeing : " + newValue.getPaidon());
			}
		};
		return listener;
    }
    
    /*
    
    private List<InvoiceToCust> getInvoices() {
		List<InvoiceToCust> customerinvoices = cus.getInvoices();
		
		  billHistory.getItems().clear();
		  billHistory.getItems().addAll(customerinvoices);
		
		if(! billHistory.getItems().isEmpty()){
			System.out.println("Bill history is not null " + billHistory.getItems().toString());
			billHistory.getItems().clear();
			//billHistoryContent.get(0).getIntocustid();
		}

		
		int size = customerinvoices.size();
		System.out.println("Customer invoices are: " + size );
		for(InvoiceToCust i : customerinvoices){
			System.out.println(i.getIntocustid());
		}
		
		
		return customerinvoices;
	}

*/
    public void getInvoices(){
    	
    	List<InvoiceToCust> customerinvoices = null;
		customerinvoices = cus.getInvoices();
		
		  billHistory.getItems().clear();
		//  billHistory.getItems().addAll(customerinvoices);
		
		if(billHistory.getItems().isEmpty()){
			System.out.println("Bill history is null " );
			//billHistoryContent.get(0).getIntocustid();
		}else {
			System.out.println("Bill history is not null " + billHistory.getItems().toString());
			billHistory.getItems().clear();
		}

		
		int size = customerinvoices.size();
		System.out.println("Customer invoices are: " + size );
		for(InvoiceToCust i : customerinvoices){
			System.out.println(i.getIntocustid() + " " + i.getPaidon() + " " + i.getPaidamount());
		}
    	
		//billHistoryContent = FXCollections.observableList((List<InvoiceToCust>) customerinvoices);
		
		
		billHistoryContent.clear();
//		billHistoryContent.removeAll(billHistoryContent);
		billHistory.getItems().clear();
		billHistoryContent.addAll(customerinvoices);
		billHistory.setItems(billHistoryContent);
		
    }

    private final ListChangeListener<InvoiceToCust> projectNamesListener(){
    	ListChangeListener<InvoiceToCust> listener = new ListChangeListener<InvoiceToCust>() {
    

        @Override
        public void onChanged(Change<? extends InvoiceToCust> c) {
        	
        	System.out.println("Chaning sssss");
        	
            if (billHistory == null) {
                return;
            }
            while (c.next()) {
                if (c.wasAdded() || c.wasReplaced()) {
                    for (InvoiceToCust p : c.getAddedSubList()) {
                    	billHistoryContent.add(p);
                    }
                }
                if (c.wasRemoved() || c.wasReplaced()) {
                    for (InvoiceToCust p : c.getRemoved()) {
                    	billHistoryContent.remove(p);
                    }
                }
            }
            
        }
      };
      return listener;
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

	private void configureCustomerRecept() {
	    cKeyRecept.setText(cus.getCkey().toString());
	    cCusNameRecept.setText(cus.getName());
	    cCusAddRecept.setText(cus.getAddress());
	    cCusMobRecept.setText(cus.getMobile());
	    cCusZonRecept.setText(cus.getZone());
	   // cCusPacRecept.setText(firstPack.getPackageDetails().toString());
	    cCusPacRecept.setText(selectedPackage.getPackageDetails().toString());
	    amountRecept.setText(pro.getPayment().toString());
	    
	    Integer afterDueDate = pro.getPayment() + pro.getFine();

	    
	    feeRecept.setText(afterDueDate.toString());
		
	}

	private void configureBillingMonth() {
		pro = selectedInvoice.getProduct();
		
		bMonth.setText(pro.getMonth());
		iDate.setText(pro.getIssuedate().toString());
		dDate.setText(pro.getDuedate().toString());
		amount.setText(pro.getPayment().toString());
		fine.setText(pro.getFine().toString());
		
		

		
	}

	private void configureCustomerDetails() {
		cName.setText(cus.getName());
		cFName.setText(cus.getFathername());
		cAddress.setText(cus.getAddress());
		cMobile.setText(cus.getMobile());
		cKey.setText(cus.getCkey().toString());
		cZone.setText(cus.getZone());
		
		/*
		LinkedHashSet<WDPackage> packSet  = cus.getWdpackages();
		WDPackage firstPack = packSet.iterator().next();
		*/
		
		/*
		List<WDPackage> packList = cus.getWdpackages();
		WDPackage firstPack = packList.get(0);
		*/
		
		/*
		 * Loading packages didnt work.
		 * therefore getting is using static
		 * 
        Set<WDPackage> set = cus.getWdpackages();
    	
    	
        Iterator<WDPackage> it= set.iterator();
        System.out.println("set size is: " + set.size());
        
        while(it.hasNext()){
        	
        	WDPackage p = it.next();
        System.out.println("not empty: " + p.getPid() + " " + p.getPackageDetails());
        }
        
        WDPackage firstPack = (WDPackage) set.toArray()[0];
		*/
		
		
        //cPackage.setText(firstPack.getPackageDetails().toString());
		cPackage.setText(selectedPackage.getPackageDetails().toString());
		



		
	}

}
