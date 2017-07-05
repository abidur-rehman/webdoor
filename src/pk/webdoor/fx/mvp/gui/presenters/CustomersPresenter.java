package pk.webdoor.fx.mvp.gui.presenters;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.util.Callback;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import pk.webdoor.fx.exceptions.CustomerDeleteException;
import pk.webdoor.fx.service.CustomerService;
import pk.webdoor.fx.service.PackageService;
import pk.webdoor.model.Customer;
import pk.webdoor.model.ICustomer;
import pk.webdoor.model.InvoiceToCust;
import pk.webdoor.model.Product;
import pk.webdoor.model.WDPackage;

/**
 *
 * @author abid.ur-rehman
 */
public class CustomersPresenter implements Initializable {

    private static final Logger log = Logger.getLogger(CustomersPresenter.class.getName());

    @FXML
    private Node root;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Customer> table;

    /**
     *
     * @return
     */
    public TableView<Customer> getTable() {
        return table;
    }

    @FXML
    private TableColumn<Customer, Integer> colCkey;
    @FXML
    private TableColumn<Customer, String> colName;

    @Inject
    private CustomerService customerService;
    @Inject
    private PackageService packageService;

    private ObservableList<Customer> tableContent = FXCollections.observableArrayList();
    private final ObservableList<Customer> filteredData = FXCollections.observableArrayList();
    private List<Customer> customersList = new ArrayList<Customer>();

    @Inject
    private MainPresenter mainPresenter;

    @Inject
    private DetailsPresenter detailsPresenter;

    @Inject
    private FilterCustomersPresenter filterCustomersPresenter;

    // customerTabs.getSelectionModel();
    // @FXML private CopyOfInvoicesController invoicesContentTab;
    /**
     *
     * @return
     */
    public Node getView() {
        return root;
    }

    /**
     * Constructor showCustomerTable is created for spring integration
     */
    /*
     public CustomersPresenter() {
     tableContent.addAll(customerService.getCustomers());
     filteredData.addAll(tableContent);

     tableContent.addListener(new ListChangeListener<Customer>() {
     @Override
     public void onChanged(
     ListChangeListener.Change<? extends Customer> change) {
     updateFilteredData();
     }
     });

     }*/
    public void showCustomersTable() {
        log.info("Invoking showCustomersTable()...");
        getCustomerListTask();
        log.info("customersList size..." + customersList.size());
        //tableContent.addAll(customerService.getCustomers());
        //tableContent.addAll(customersList);
        //

        //filterCustomersPresenter.fillCustomerTable(tableContent);
        tableContent.addListener(new ListChangeListener<Customer>() {
            @Override
            public void onChanged(
                    ListChangeListener.Change<? extends Customer> change) {
                updateFilteredData();
            }
        });
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        assert table != null;
        assert colName != null;
        assert colCkey != null;

        // configureButtons();
        // configureInvoiceDetails();
        configureCustomerTable();
        // configureInvoiceTable();
        // configureProductsTable();
        // showDetailsTab();
        //showCustomersTable();

    }

    /**
     *
     */
    public static DataFormat dataFormat = new DataFormat("mycell");

    /**
     *
     * @param event
     */
    public void saveCustomerFired(ActionEvent event) {
        detailsPresenter.updateCustomerDetails();

    }

    private void configureCustomerTable() {

        colCkey.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("ckey"));
        colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));

        colName.setPrefWidth(75);
        colCkey.setPrefWidth(75);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // tableContent = getTableContents();
        // tableContent = (List<Customer>) service.getCustomers();
        // table.setItems(getCustomerList());
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
        // table.getSelectionModel().selectedItemProperty().addListener(invoicesContentTab);

        assert table.getItems() == tableContent;

    }

    // Connect to the model, get the project's names list, and listen to
    // its changes. Initializes the list widget with retrieved project names.
    /**
     *
     * @return tableContent
     */
    public ObservableList<Customer> getCustomerList() {
        // filteredData.addAll(service.getCustomers());
        //getCustomerListTask();
        tableContent = FXCollections.observableList((List<Customer>) customersList);
        return tableContent;
    }

    private void getCustomerListTask() {
        log.info("Invoking getCustomerListTask()....");
        final Task<ObservableList> customerListTask = new Task<ObservableList>() {
            @Override
            protected ObservableList<Customer> call() throws Exception {
                return FXCollections.observableList((List<Customer>) customerService.getCustomers());
            }
        };
        customerListTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    int selectedRowIndex = 0;
                    if (!table.getItems().isEmpty()) {
                        selectedRowIndex = table.getSelectionModel().getSelectedIndex();
                        table.getItems().clear();
                        tableContent.clear();
                        filteredData.clear();
                    }
                    tableContent.setAll(customerListTask.getValue());
                    filteredData.setAll(tableContent);
                    /**
                     * During deletion the selected row index is the last one
                     * therefore setting it to the last element of the table
                     */
                    if (selectedRowIndex == tableContent.size()) {
                        selectedRowIndex = tableContent.size() - 1;
                    }
                    table.getSelectionModel().select(selectedRowIndex);
                    filterCustomersPresenter.fillCustomerTable(tableContent);
                }
            }
        });

        new Thread(customerListTask).start();
    }

    private void getCustomerListTask2() {
        log.info("Invoking getCustomerListTask2()....");
        final Task<List> customerListTask = new Task<List>() {
            @Override
            protected List<Customer> call() throws Exception {
                return customerService.getCustomers();
            }
        };
        customerListTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    customersList.addAll(customerListTask.getValue());
                    log.info("CustomerList size " + customersList.size());
                }
            }
        });
        new Thread(customerListTask).start();
    }

    // @FXML CopyOfInvoicesController invoicesContentTab;
    protected final ChangeListener<Customer> customerChangeListener() {
        ChangeListener<Customer> listener = new ChangeListener<Customer>() {

            @Override
            public void changed(ObservableValue<? extends Customer> observable,
                    Customer oldValue, Customer newValue) {

				// invoicesContentTab.customerSelected(newValue);
                // CopyOfInvoicesController invoicesContentTab2 = new
                // CopyOfInvoicesController();
                // invoicesContentTab2.setText(newValue.getName());
                // invoicesContentTab.getChildren().add(invoicesContentTab2);
				/*
                 * Parent root = null; try { root =
                 * FXMLLoader.load(this.getClass
                 * ().getResource("CopyofInvoices.fxml")); } catch (IOException
                 * e) { // TODO Auto-generated catch block e.printStackTrace();
                 * } TextField lblData = (TextField)
                 * root.lookup("IIdDetailsText"); if (lblData!=null)
                 * lblData.setText("Hello");
                 */

                /*
                 * FXMLLoader fxmlLoader = new FXMLLoader(); try { Parent p =
                 * (Parent)
                 * fxmlLoader.load(getClass().getResource("CopyofInvoices.fxml"
                 * ).openStream()); } catch (IOException e) { // TODO
                 * Auto-generated catch block e.printStackTrace(); }
                 * CopyOfInvoicesController fooController =
                 * (CopyOfInvoicesController) fxmlLoader.getController();
                 * 
                 * fooController.setText("hello");
                 */
                /*
                 * Scene scene = table.getScene(); FXMLLoader fxmlLoader = new
                 * FXMLLoader(getClass().getResource("CopyofInvoices.fxml"));
                 * Parent root = null; try { root = (Parent) fxmlLoader.load();
                 * } catch (IOException e) { // TODO Auto-generated catch block
                 * e.printStackTrace(); }
                 * 
                 * CopyOfInvoicesController fooController =
                 * (CopyOfInvoicesController) fxmlLoader.getController();
                 */
                // CopyOfInvoicesController.setText(newValue.getName());
                mainPresenter.customerChanged(newValue);

            }

        };
        return listener;

    }

    /**
     *
     */
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

    /**
     *
     * If customer name or cKey (customer id) matches filter it will be returned
     * otherwise false will be returned
     *
     * @param customer
     * @return boolean
     */
    protected boolean matchesFilter(Customer customer) {
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
        } else if (customer.getCkey().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (customer.getCnic().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Customer, ?>> sortOrder = new ArrayList<>(table.getSortOrder());
        table.getSortOrder().clear();
        table.getSortOrder().addAll(sortOrder);
    }

    /**
     *
     * @return
     */
    public Customer getSelectedCustomer() {
        if (table != null) {
            List<Customer> selectedCustomers = table.getSelectionModel().getSelectedItems();
            if (selectedCustomers.size() == 1) {
                final Customer selectedCustomer = selectedCustomers.get(0);
                return selectedCustomer;
            }
        }
        return null;
    }

    /**
     *
     * @param event
     */
    public void newCustomerFired(ActionEvent event) {
        mainPresenter.displayMessage("New Customer created. Please complete details and Save it");
        Customer newCustomer = new Customer();
        newCustomer.setCkey(0);
        newCustomer.setName("Enter Name");
        //PackageService packageService = new PackageService();
        //Set<WDPackage> wdpackages = new HashSet<>(packageService.getPackages());
        Set<WDPackage> wdpackages = getPackagesTask();
        newCustomer.setWdpackages(wdpackages);
        newCustomer.setFathername("Enter Fathername");

        if (table != null) {
            // Select the newly created issue.
            table.getSelectionModel().clearSelection();
            tableContent.add(newCustomer);
            // table.getSelectionModel().
            table.getSelectionModel().select(newCustomer);
        }
        //MessagesController.displayMessage("New Customer created. Please complete details and Save it");
    }

    private Set<WDPackage> getPackagesTask() {
        final Set<WDPackage> wdpackages = new HashSet<>();
        final Task<Set<WDPackage>> packagesTask = new Task<Set<WDPackage>>() {
            @Override
            protected Set<WDPackage> call() throws Exception {
                Set<WDPackage> wdpackages = new HashSet<>(packageService.getPackages());
                return wdpackages;
            }

        };

        packagesTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    wdpackages.addAll(packagesTask.getValue());
                }
            }
        });
        new Thread(packagesTask).start();
        return wdpackages;
    }

    /**
     *
     * @param event
     */
    public void deleteCustomerFiredOld(ActionEvent event) {
        Customer deleteCustomer = getSelectedCustomer();

        String deleteResult;
        try {
            deleteResult = customerService.deleteCustomer(deleteCustomer);
            if (deleteResult.equals("FAILED")) {
                throw new CustomerDeleteException(deleteResult);
            } else {
                mainPresenter.displayMessage("Customer deleted Successfully");
                refreshAfterDelete(deleteCustomer);
            }
        } catch (CustomerDeleteException e) {
        }
    }

    public void deleteCustomerFired(ActionEvent Event) {
        final Customer deleteCustomer = getSelectedCustomer();
        final Task<String> deleteCustomerTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return customerService.deleteCustomer(deleteCustomer);
            }
        };

        deleteCustomerTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    if (!"SUCCESS".equals(deleteCustomerTask.getValue())) {
                        //mainPresenter.displayErrorMessage("Customer update failed. Please Try again!");
                        //customersPresenter.showCustomersTable();
                        //setSelectedCustomer(getFormCustomer());
                        mainPresenter.displayErrorMessage(deleteCustomerTask.getValue());
                    } else {
                        mainPresenter.displayMessage("Customer deleted Successfully");
                        //customerPresenter.refreshAfterSave();
                        showCustomersTable();
                    }
                }
            }
        });

        new Thread(deleteCustomerTask).start();

    }

    private void refreshAfterDelete(Customer deleteCustomer) {
        table.getSelectionModel().clearSelection();
        //table.setItems(getCustomerList());
        //filteredData.remove(deleteCustomer); 
        table.getItems().clear();
        tableContent.clear();
        tableContent.addAll(customerService.getCustomers());
        //getCustomerListTask();
        tableContent.addAll(customersList);
        //table.setItems(filteredData);

        filterCustomersPresenter.fillCustomerTable(tableContent);
    }

    /**
     *
     */
    public void refreshAfterSave() {
        int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
        /*
         table.getItems().clear();
         table.setItems(getCustomerList());
         */
        table.getItems().clear();
        tableContent.clear();
        tableContent.addAll(customerService.getCustomers());
        //tableContent.addAll(customersList);
        //tableContent.addAll(getCustomerList());
        table.getSelectionModel().select(selectedRowIndex);

        filterCustomersPresenter.fillCustomerTable(tableContent);

        // updateDetails();
        // resetFields();
        detailsPresenter.updateSaveCustomerButtonState();

    }

    public void refreshAfterSave2() {
        int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
        table.getSelectionModel().select(selectedRowIndex);

        filterCustomersPresenter.fillCustomerTable(tableContent);

        // updateDetails();
        // resetFields();
        detailsPresenter.updateSaveCustomerButtonState();

    }

    private SaveState computeInvoiceSaveState(InvoiceToCust invoice1,
            InvoiceToCust invoice2) {
        try {
			// These fields are not editable - so if they differ they are
            // invalid
            // and we cannot save.
			/*
             * if (!equal(edited.getCid(), selectedCustomer.getCid())) { return
             * SaveState.INVALID; }
             */
            // If these fields differ, the issue needs saving.

            if (!equal(invoice1.getStatus(), invoice2.getStatus())) {
                return SaveState.UNSAVED;
            }

            if (!equal(invoice1.getPaidon(), invoice2.getPaidon())) {
                return SaveState.UNSAVED;
            }

            if (!equal(invoice1.getPaidamount(), invoice2.getPaidamount())) {
                return SaveState.UNSAVED;
            }

        } catch (Exception x) {
            // If there's an exception, some fields are invalid.
            return SaveState.INVALID;
        }
        // No field is invalid, no field needs saving.
        return SaveState.UNCHANGED;
    }

    private static enum SaveState {

        INVALID, UNSAVED, UNCHANGED
    }

    private SaveState computeSaveState(ICustomer edited,
            ICustomer selectedCustomer) {
        /*
         * edited.getFathername() + " " + edited.getUsername() +" " +
         * edited.getPassword() + edited.getTelephone() + " " +
         * edited.getMobile() + " " + edited.getZone() + " " +
         * edited.getAddress());
         * 
         * selectedCustomer.getFathername() + " " +
         * selectedCustomer.getUsername() +" " + selectedCustomer.getPassword()
         * + selectedCustomer.getTelephone() + " " +
         * selectedCustomer.getMobile() + " " + selectedCustomer.getZone() + " "
         * + selectedCustomer.getAddress());
         */
        try {
			// These fields are not editable - so if they differ they are
            // invalid
            // and we cannot save.
			/*
             * if (!equal(edited.getCid(), selectedCustomer.getCid())) { return
             * SaveState.INVALID; }
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

}
