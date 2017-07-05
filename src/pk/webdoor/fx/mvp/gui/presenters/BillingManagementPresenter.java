package pk.webdoor.fx.mvp.gui.presenters;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.inject.Inject;
import pk.webdoor.fx.mvp.gui.WebdoorApp;
import pk.webdoor.fx.service.ProductService;
import pk.webdoor.model.Product;

public class BillingManagementPresenter implements Initializable {

    private static final Logger log = Logger.getLogger(BillingManagementPresenter.class.getName());

    @FXML
    private Node root;

    @FXML
    private TableColumn<Product, Integer> mID;
    @FXML
    private TableColumn<Product, String> mMonth;
    @FXML
    private TableColumn<Product, String> mIssueDate;
    @FXML
    private TableColumn<Product, String> mDueDate;
    @FXML
    private TableColumn<Product, Integer> mPayment;
    @FXML
    private TableColumn<Product, Integer> mFine;
    @FXML
    private TableView<Product> monthsTable;

    private ObservableList<Product> productsTableContents = FXCollections.observableArrayList();

    @Inject
    public ProductService productService;

    @Inject
    private MainPresenter mainPresenter;

    @Inject
    private InvoicesPresenter invoicePresenter;

    @Inject
    private MonthDialogPresenter monthDialogPresenter;
    private Stage primaryStage;
    private WebdoorApp mainApp;

    private List<Product> monthsList = new ArrayList<>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        log.info("Billing Management Presenter called....");
        configureColumns();
    }

    public Node getView() {
        return root;
    }

    private void configureColumns() {
        mID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("prodid"));
        mMonth.setCellValueFactory(new PropertyValueFactory<Product, String>("month"));
        mIssueDate.setCellValueFactory(new PropertyValueFactory<Product, String>("issuedate"));
        mDueDate.setCellValueFactory(new PropertyValueFactory<Product, String>("duedate"));
        mPayment.setCellValueFactory(new PropertyValueFactory<Product, Integer>("payment"));
        mFine.setCellValueFactory(new PropertyValueFactory<Product, Integer>("fine"));

        log.info("mID: " + mID);
        mID.setPrefWidth(75);

        //productsTableContents = FXCollections.observableList((List<Product>) productService.getAllProducts());
        monthsTable.setItems(productsTableContents);
    }

    protected void fillProductsTable() {
        getObservableMonthListTask();
        /* This was old version before Task Implementation   
         *
         productsTableContents = FXCollections.observableList((List<Product>) productService.getAllProducts());
         monthsTable.setItems(productsTableContents);
         invoicePresenter.fillProductsTable(productsTableContents);
         */
    }

    private void getObservableMonthListTask() {
        log.info("Invoking getObservableMonthListTask()....");
        final Task<ObservableList> monthsListTask = new Task<ObservableList>() {
            @Override
            protected ObservableList<Product> call() throws Exception {
                return FXCollections.observableList((List<Product>) productService.getAllProducts());
            }
        };
        monthsListTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    if (!monthsTable.getItems().isEmpty()) {
                        monthsTable.getItems().clear();
                        productsTableContents.clear();
                    }
                    productsTableContents.setAll(monthsListTask.getValue());
                    invoicePresenter.fillProductsTable(productsTableContents);
                }
            }
        });

        new Thread(monthsListTask).start();
    }

    private String newMonth;
    private String newIssueDate;

    public void createNewMonth2(ActionEvent event) {
        log.info("newCustomerFired Called()....");
        List<Product> allMonths = productService.getAllProducts();
        Product p2 = allMonths.get(allMonths.size() - 1);
        // System.out.println(p2.getMonth());
        Product product = new Product();
        try {
            String lastMonth = p2.getMonth();
            log.info(lastMonth);
            getNewMonth(lastMonth);
            if (newMonth != null && newIssueDate != null) {
                product.setMonth(newMonth);
                product.setIssuedate(newIssueDate);
                product.setDuedate(newIssueDate);
                product.setPayment(699);
                product.setFine(50);

                mainPresenter.displayMessage("New Month created for Billing");
            } else {
                mainPresenter.displayMessage("All months for 2013 already created. Please consult your system admin");
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void createNewMonth(Product product) {
        log.info("newCustomerFired Called()....");
        createNewMonthTask(product);
    }

    private void createNewMonthTask(final Product product) {
        final Task<String> createMonthTask = new Task<String>() {

            @Override
            protected String call() throws Exception {
                return productService.updateProduct(product);
            }
        };
        createMonthTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    if (!"SUCCESS".equals(createMonthTask.getValue())) {
                        //mainPresenter.displayErrorMessage("Customer update failed. Please Try again!");
                        //customersPresenter.showCustomersTable();
                        //setSelectedCustomer(getFormCustomer());
                        mainPresenter.displayErrorMessage(createMonthTask.getValue());
                    } else {
                        mainPresenter.displayMessage("New Month created for Billing Successfully");
                        //customerPresenter.refreshAfterSave();
                        updateAfterCreation();
                    }
                }
            }
        });
        new Thread(createMonthTask).start();
    }

    private void getAllMonthsTask() {

        final Task<List> monthsListTask = new Task<List>() {

            @Override
            protected List call() throws Exception {
                return productService.getAllProducts();
            }
        };
        monthsListTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    monthsList.addAll(monthsListTask.getValue());
                    log.info("monthsList size " + monthsList.size());
                }
            }
        });
        new Thread(monthsListTask).start();
    }

    @FXML
    private void showMonthDialog(ActionEvent event) {
        Product tempProduct = null;
        boolean okClicked = showMonthCreationDialog(tempProduct, event);
        if (okClicked) {
            //mainApp.getPersonData().add(tempPerson);

        }
    }

    @FXML
    private void editMonthDialog(ActionEvent event) {
        log.info("Invoking editMonthDialog()...");
        Product tempProduct = monthsTable.getSelectionModel().getSelectedItem();
        if (tempProduct != null) {
            boolean okClicked = showMonthCreationDialog(tempProduct, event);
            if (okClicked) {
            }

        } else {
            // Nothing selected
            Dialogs.showWarningDialog((Stage) ((Node) event.getSource()).getScene().getWindow(),
                    "Please select a month in the table.",
                    "No Month Selected", "No Selection");
        }
    }

    @FXML
    private void deleteMonth(ActionEvent event) {
        log.info("Invoking deleteMonth()...");
        int selectedIndex = monthsTable.getSelectionModel().getSelectedIndex();
        Product tempProduct = monthsTable.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            String result = "";
            result = productService.deleteProduct(tempProduct);
            if(result.equals("SUCCESS")) {
            monthsTable.getItems().remove(selectedIndex);
            } else {
            mainPresenter.displayErrorMessage(result);
            }
        } else {
            // Nothing selected
            Dialogs.showWarningDialog((Stage) ((Node) event.getSource()).getScene().getWindow(),
                    "Please select a month in the table.",
                    "No Month Selected", "No Selection");
        }
    }

    private boolean showMonthCreationDialog(Product selectedProduct, ActionEvent event) {
        try {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Month details");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());
            Scene scene = getScene();
            scene.getStylesheets().add("/resources/webdoor.css");
            dialogStage.setScene(scene);
            monthDialogPresenter.setDialogStage(dialogStage);
            monthDialogPresenter.setSelectedProduct(selectedProduct);
            dialogStage.showAndWait();
            return monthDialogPresenter.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private Scene getScene() {
        Group root = new Group();
        AnchorPane page = null;
        page = (AnchorPane) monthDialogPresenter.getView();
        Scene scene = new Scene(root);
        root.getChildren().add(page);
        return (scene);
    }

    public void createNewMonth3(ActionEvent event) {
        log.info("newCustomerFired Called()....");
        List<Product> allMonths = productService.getAllProducts();
        Product p2 = allMonths.get(allMonths.size() - 1);
        // System.out.println(p2.getMonth());
        Product product = new Product();
        try {
            String lastMonth = p2.getMonth();
            log.info(lastMonth);
            getNewMonth(lastMonth);
            if (newMonth != null && newIssueDate != null) {
                product.setMonth(newMonth);
                product.setIssuedate(newIssueDate);
                product.setDuedate(newIssueDate);
                product.setPayment(699);
                product.setFine(50);
                productService.updateProduct(product);
                updateAfterCreation();
                mainPresenter.displayMessage("New Month created for Billing");
            } else {
                mainPresenter.displayMessage("All months for 2013 already created. Please consult your system admin");
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // product.getMainPresenter().displayMessage("New Month created");
    }

    private void updateAfterCreation() {
        fillProductsTable();
        /*
         Old version before Task Implementation
         */
        //monthsTable.getItems().clear();
        //productsTableContents = FXCollections.observableList((List<Product>) productService.getAllProducts());
        //monthsTable.setItems(productsTableContents);
        //invoicePresenter.refreshAfterMonthCreation(productsTableContents);

        // getInvoicePresenter().refreshAfterMonthCreation();
    }

    private void getNewMonth(String oldMonth) {
        switch (oldMonth) {
            case "JANUARY": {
                newIssueDate = "01-02-2013";
                newMonth = "FEBRUARY";
                break;
            }
            case "FEBRUARY": {
                newIssueDate = "01-03-2013";
                newMonth = "MARCH";
                break;
            }
            case "MARCH": {
                newIssueDate = "01-04-2013";
                newMonth = "APRIL";
                break;
            }
            case "APRIL": {
                newIssueDate = "01-05-2013";
                newMonth = "MAY";
                break;
            }
            case "MAY": {
                newIssueDate = "01-06-2013";
                newMonth = "JUNE";
                break;
            }
            case "JUNE": {
                newIssueDate = "01-07-2013";
                newMonth = "JULY";
                break;
            }
            case "JULY": {
                newIssueDate = "01-08-2013";
                newMonth = "AUGUST";
                break;
            }
            case "AUGUST": {
                newIssueDate = "01-09-2013";
                newMonth = "SEPTEMBER";
                break;
            }
            case "SEPTEMBER": {
                newIssueDate = "01-10-2013";
                newMonth = "OCTOBER";
                break;
            }
            case "OCTOBER": {
                newIssueDate = "01-11-2013";
                newMonth = "NOVEMBER";
                break;
            }
            case "NOVEMBER": {
                newIssueDate = "01-12-2013";
                newMonth = "DECEMBER";
                break;
            }
            default: {
                newIssueDate = null;
                newMonth = null;
                break;
            }
        }

    }

}
