/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.webdoor.fx.mvp.gui.presenters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javax.inject.Inject;
import pk.webdoor.fx.service.CustomerService;
import pk.webdoor.model.Customer;
import org.google.jhsheets.filtered.FilteredTableView;
import org.google.jhsheets.filtered.operators.IFilterOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;
import org.google.jhsheets.filtered.tablecolumn.ColumnFilterEvent;
import org.google.jhsheets.filtered.tablecolumn.FilterableIntegerTableColumn;
import org.google.jhsheets.filtered.tablecolumn.FilterableStringTableColumn;

/**
 *
 * @author abid.ur-rehman
 */
public class FilterCustomersPresenter implements Initializable {

    @FXML
    private Node root;

    @FXML
    private FilteredTableView<Customer> filteredTable;
    @FXML
    private FilterableIntegerTableColumn<Customer, Integer> colCkey;
    @FXML
    private FilterableStringTableColumn<Customer, String> colCnic;
    @FXML
    private FilterableStringTableColumn<Customer, String> colName;
    @FXML
    private FilterableStringTableColumn<Customer, String> colFathername;
    @FXML
    private FilterableStringTableColumn<Customer, String> colUsername;
    @FXML
    private FilterableStringTableColumn<Customer, String> colZone;
    @FXML
    private FilterableStringTableColumn<Customer, String> colDor;    
    /*
     @FXML
     private TableColumn<Customer, String> colPassword;
     @FXML
     private TableColumn<Customer, String> colZone;
     @FXML
     private TableColumn<Customer, String> colDor;
     @FXML
     private TableColumn<Customer, String> colMobile;
     */

    @Inject 
    CustomersPresenter customerPresenter;
    
    @Inject
    private CustomerService customerService;

    private ObservableList<Customer> tableContent = FXCollections.observableArrayList();

    /**
     *
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureCustomerTable();
        //exampleInit();
        //configureTableNew();
    }

    /**
     *
     * @return
     */
    public Node getView() {
        return root;
    }

    /**
     * Constructor
     */
    /*
    public FilterCustomersPresenter() {
        tableContent.addAll(service.getCustomers());
        table = new FilteredTableView<>(tableContent);
    }*/

    private void configureCustomerTable() {
        //tableContent = getCustomerList();
        //filteredTable.getItems().addAll(tableContent);
        filteredTable.addEventHandler(ColumnFilterEvent.FILTER_CHANGED_EVENT, new EventHandler<ColumnFilterEvent>() {
            @Override
            public void handle(ColumnFilterEvent t) {
                System.out.println("Filtered column count: " + filteredTable.getFilteredColumns().size());
                System.out.println("Filtering changed on column: " + t.sourceColumn().getText());
                System.out.println("Current filters on column " + t.sourceColumn().getText() + " are:");
                final List<IFilterOperator> filters = t.sourceColumn().getFilters();
                for (IFilterOperator filter : filters) {
                    System.out.println("  Type=" + filter.getType() + ", Value=" + filter.getValue());
                }

                applyCustomerFilters();
            }
        });
    }
    
    protected void fillCustomerTable(ObservableList<Customer> tableContent){
        //tableContent = getCustomerList();
        filteredTable.getItems().clear();
        filteredTable.getItems().addAll(tableContent);
        this.tableContent = tableContent;
        filteredTable.getSelectionModel().selectedItemProperty().addListener(customerPresenter.customerChangeListener());
    }

    private void applyCustomerFilters() {
        try {
            final ObservableList<Customer> newData = FXCollections.observableArrayList();
            newData.addAll(tableContent);
            System.out.println("Before: " + newData.size());
            filterKeyColumn(newData);
            filterColumn(newData, "getCnic");
            filterColumn(newData, "getName");
            filterColumn(newData, "getFathername");
            filterColumn(newData, "getUsername");
            filterColumn(newData, "getZone");
            filterColumn(newData, "getDor");
            System.out.println("After: " + newData.size());
            // Display the filtered results
            //tableContent.clear();
            //filteredTable.getItems().clear();
            //filteredTable.getItems().addAll(newData);
            filteredTable.getItems().setAll(newData);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FilterCustomersPresenter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FilterCustomersPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void filterKeyColumn(ObservableList<Customer> newData) {
        final List<Customer> remove = new ArrayList<>();
        final ObservableList<NumberOperator<Integer>> filters = colCkey.getFilters();

        for (NumberOperator<Integer> filter : filters) {
            for (Customer item : newData) {
                // Note: not all Type's are supported for each Operator.
                // Look at the validTypes() method to see what types are available.
                if (filter.getType() == NumberOperator.Type.EQUALS) {
                    if (item.getCkey() != filter.getValue()) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
                    if (item.getCkey() == filter.getValue()) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
                    if (item.getCkey() <= filter.getValue()) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
                    if (item.getCkey() < filter.getValue()) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
                    if (item.getCkey() >= filter.getValue()) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
                    if (item.getCkey() > filter.getValue()) {
                        remove.add(item);
                    }
                }
            }
        }
        newData.removeAll(remove);
    }

    private void filterColumn(ObservableList<Customer> newData, String column) throws IllegalAccessException, InvocationTargetException {
        try {
            // Here's an example of how you could filter the ID column
            final List<Customer> remove = new ArrayList<>();
            remove.clear();
            ObservableList<StringOperator> filters = null;
            if(column.equals("getCnic")) {
                filters = colCnic.getFilters();
            } else if (column.equals("getName")){
                filters = colName.getFilters();
            } else if (column.equals("getFathername")){
                filters = colFathername.getFilters();
            } else if (column.equals("getUsername")){
                filters = colUsername.getFilters();
            } else if (column.equals("getZone")){
                filters = colZone.getFilters();
            } else if (column.equals("getDor")){
                filters = colDor.getFilters();
            } 
            
            for (StringOperator filter : filters) {
                for (Customer item : newData) {
                    // Note: not all Type's are supported for each Operator.
                    // Look at the validTypes() method to see what types are available.
                    String filterValue = filter.getValue().toString();
                    String itemValue = getCutomerMethodValue(item, column);
                    //final String itemValue = item.getCnic();
                    if (filter.getType() == StringOperator.Type.EQUALS) {
                        if (!filterValue.equals(itemValue)) {
                            remove.add(item);
                        }
                    } else if (filter.getType() == StringOperator.Type.NOTEQUALS) {
                        if (filterValue.equals(itemValue)) {
                            remove.add(item);
                        }
                    } else if (filter.getType() == StringOperator.Type.CONTAINS) {
                        if (!itemValue.contains(filterValue)) {
                            remove.add(item);
                        }
                    } else if (filter.getType() == StringOperator.Type.STARTSWITH) {
                        if (!itemValue.toString().startsWith(filterValue)) {
                            remove.add(item);
                        }
                    } else if (filter.getType() == NumberOperator.Type.ENDSWITH) {
                        if (!itemValue.toString().endsWith(filterValue)) {
                            remove.add(item);
                        }
                    }
                }
            }
            newData.removeAll(remove);
        } catch (SecurityException ex) {
            Logger.getLogger(FilterCustomersPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getCutomerMethodValue(Customer customer, String column) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object result = "";
        try {
            Class customerClass = Customer.class;
            Method method = customerClass.getMethod(column);
            result = method.invoke(customer);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(FilterCustomersPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result.toString();
    }

    private ObservableList<Customer> getCustomerList() {
        System.out.println("getCustomerList called...");
        // filteredData.addAll(service.getCustomers());
        return FXCollections.observableList((List<Customer>) customerService.getCustomers());
    }
}
