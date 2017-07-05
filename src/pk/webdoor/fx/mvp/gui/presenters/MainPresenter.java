package pk.webdoor.fx.mvp.gui.presenters;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import javax.inject.Inject;
import pk.webdoor.model.Customer;

public class MainPresenter {

    @FXML
    private Parent root;
    @FXML
    private BorderPane customersPane;

    @FXML
    Label messageBar;

    @FXML
    Button newCustomer;
    @FXML
    Button deleteCustomer;
    @FXML
    Button saveCustomer;

    @FXML
    private Tab detailsTab;
    @FXML
    private Tab invoicesTab;
    @FXML
    private Tab createBill;
    @FXML
    private Tab filterCustomerTab;

    @Inject
    private CustomersPresenter customersPresenter;
    @Inject
    private DetailsPresenter detailsPresenter;
    @Inject
    private InvoicesPresenter invoicesPresenter;
    @Inject
    private BillingManagementPresenter billManagePresenter;
    @Inject
    private FilterCustomersPresenter filterCustomerPresenter;

    private Customer selectedCustomer;

    /*
     public void setCustomersPresenter(CustomersPresenter customersPresenter) {
     this.customersPresenter = customersPresenter;
     }
     */
    public Parent getView() {
        return root;
    }

    public void configureApp() {
        configureButtons();
        showCustomers();
        showTabs();
        fillTables();
    }

    private void configureButtons() {
        // if (selectedCustomer != null) {
        newCustomer.setDisable(false);
        deleteCustomer.setDisable(false);
        saveCustomer.setDisable(true);
        // }
    }

    private void showTabs() {
        detailsTab.setContent(detailsPresenter.getView());
        invoicesTab.setContent(invoicesPresenter.getView());
        createBill.setContent(billManagePresenter.getView());
        filterCustomerTab.setContent(filterCustomerPresenter.getView());
    }

    public void showCustomers() {
        customersPane.setCenter(customersPresenter.getView());
        customersPresenter.showCustomersTable();
        //invoicesPresenter.fillProductsTable();

    }

    private void fillTables() {
        billManagePresenter.fillProductsTable();
    }

    public void selectedCustomer(Customer customer) {

    }

    public void newCustomerFired(ActionEvent event) {
        //getCustomersPresenter().newCustomerFired(event);
        customersPresenter.newCustomerFired(event);
    }

    public void saveCustomerFired(ActionEvent event) {
        //getCustomersPresenter().saveCustomerFired(event);
        customersPresenter.saveCustomerFired(event);
    }

    public void deleteCustomerFired(ActionEvent event) {
        //getCustomersPresenter().deleteCustomerFired(event);
        customersPresenter.deleteCustomerFired(event);
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void customerChanged(Customer newCusotmer) {
        setSelectedCustomer(newCusotmer);
        detailsPresenter.setSelectedCustomer(newCusotmer);
        invoicesPresenter.setSelectedCustomer(newCusotmer);
    }
    /*
     public CustomersPresenter getCustomersPresenter() {
     return customersPresenter;
     }
     */

    private FadeTransition messageTransition = null;

    public void displayMessage(String message) {
        if (messageBar != null) {
            if (messageTransition != null) {
                messageTransition.stop();
            } else {
                messageTransition = new FadeTransition(Duration.millis(2000), messageBar);
                messageTransition.setFromValue(1.0);
                messageTransition.setToValue(0.0);
                messageTransition.setDelay(Duration.millis(1000));
                messageTransition.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        messageBar.setVisible(false);
                    }
                });
            }
            messageBar.setText(message);
            messageBar.setVisible(true);
            messageBar.setOpacity(1.0);
            messageBar.setTextFill(Paint.valueOf("linear-gradient(#61a2b1, #2A5058)"));
            messageTransition.playFromStart();
        }
    }

    public void displayErrorMessage(String message) {
        if (messageBar != null) {
            if (messageTransition != null) {
                messageTransition.stop();
            } else {
                messageTransition = new FadeTransition(Duration.millis(2000), messageBar);
                messageTransition.setFromValue(1.0);
                messageTransition.setToValue(0.0);
                messageTransition.setDelay(Duration.millis(1000));
                messageTransition.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        messageBar.setVisible(false);
                    }
                });
            }
            messageBar.setText(message);
            messageBar.setVisible(true);
            messageBar.setOpacity(1.0);
            messageBar.setTextFill(Color.RED);
            messageTransition.playFromStart();
        }
    }

}
