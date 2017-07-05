/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.webdoor.fx.mvp.gui.presenters;

import java.text.ParseException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import pk.webdoor.model.Product;

/**
 *
 * @author abid.ur-rehman
 */
public class MonthDialogPresenter {

    private static final Logger log = Logger.getLogger(MonthDialogPresenter.class.getName());

    @FXML
    private Parent root;
    @FXML
    private TextField monthField;
    @FXML
    private TextField issueDateField;
    @FXML
    private TextField dueDateField;
    @FXML
    private TextField paymentField;
    @FXML
    private TextField afterDueDateField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private Product selectedProduct;

    @Inject
    BillingManagementPresenter billingManagementPresenter;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        configureFields();

    }

    private void configureFields() {
        monthField.setText("");
        issueDateField.setPromptText("dd-mm-yyyy");
        dueDateField.setPromptText("dd-mm-yyyy");
        paymentField.setText("");
        afterDueDateField.setText("");
    }

    public Parent getView() {
        return root;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleSave() {
        log.info("Invoking handelSave()...");
        if (isInputValid()) {
            try {
                okClicked = true;
                dialogStage.close();
                Product product;
                if (selectedProduct != null) {
                    product = selectedProduct;
                } else {
                    product = new Product();
                }
                product.setMonth(monthField.getText());
                product.setIssuedate(issueDateField.getText());
                product.setDuedate(dueDateField.getText());
                product.setPayment(Integer.parseInt(paymentField.getText()));
                product.setFine(Integer.parseInt(afterDueDateField.getText()));
                billingManagementPresenter.createNewMonth(product);
            } catch (ParseException ex) {
                log.error(ex);
            }
        }
    }

    @FXML
    private void handleCancel() {
        log.info("Invoking handleCancel()...");
        dialogStage.close();
    }

    @FXML
    private void handleDelete() {
        log.info("Invoking handleCancel()...");
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (monthField.getText() == null || monthField.getText().length() == 0) {
            errorMessage += "No valid month name!\n";
        }

        if (issueDateField.getText() == null || issueDateField.getText().length() == 0) {
            errorMessage += "No valid issue date!\n";
        } else {
            if (!CalendarUtil.validString(issueDateField.getText())) {
                errorMessage += "No valid issue date. Use the format dd-mm-yyyy!\n";
            }
        }

        if (dueDateField.getText() == null || dueDateField.getText().length() == 0) {
            errorMessage += "No valid due date!\n";
        } else {
            if (!CalendarUtil.validString(dueDateField.getText())) {
                errorMessage += "No valid due date. Use the format dd-mm-yyyy!\n";
            }
        }

        if (paymentField.getText() == null || paymentField.getText().length() == 0) {
            errorMessage += "No valid payment!\n";
        } else {
            // try to parse the postal code into an int
            try {
                Integer.parseInt(paymentField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid payment (must be an integer)!\n";
            }
        }

        if (afterDueDateField.getText() == null || afterDueDateField.getText().length() == 0) {
            errorMessage += "No valid fee!\n";
        } else {
            // try to parse the postal code into an int
            try {
                Integer.parseInt(afterDueDateField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid fee (must be an integer)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message
            Dialogs.showErrorDialog(dialogStage, errorMessage,
                    "Please correct invalid fields", "Invalid Fields");
            return false;
        }
    }

    protected void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        if (selectedProduct != null) {
            monthField.setText(selectedProduct.getMonth());
            issueDateField.setText(selectedProduct.getIssuedate());
            dueDateField.setText(selectedProduct.getDuedate());
            paymentField.setText(selectedProduct.getPayment().toString());
            afterDueDateField.setText(selectedProduct.getFine().toString());
        }
    }

}
