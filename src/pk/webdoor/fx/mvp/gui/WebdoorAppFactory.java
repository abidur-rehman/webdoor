package pk.webdoor.fx.mvp.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import pk.webdoor.fx.mvp.gui.presenters.BillingManagementPresenter;
import pk.webdoor.fx.mvp.gui.presenters.CustomersPresenter;
import pk.webdoor.fx.mvp.gui.presenters.DetailsPresenter;
import pk.webdoor.fx.mvp.gui.presenters.FilterCustomersPresenter;
import pk.webdoor.fx.mvp.gui.presenters.InvoicesPresenter;
import pk.webdoor.fx.mvp.gui.presenters.MainPresenter;
import pk.webdoor.fx.mvp.gui.presenters.MessagesPresenter;
import pk.webdoor.fx.service.CustomerService;
import pk.webdoor.fx.service.PackageService;
import pk.webdoor.fx.service.ProductService;

public class WebdoorAppFactory {

    /*
    private MainPresenter mainPresenter;
    private CustomersPresenter customersPresenter;
    private DetailsPresenter detailsPresenter;
    private InvoicesPresenter invoicesPresenter;
    private BillingManagementPresenter billManagementPresenter;
    private MessagesPresenter messagePresenter;
    private FilterCustomersPresenter filterCustomerPresenter;

    private CustomerService customerService;
    public ProductService productService;
    private PackageService packageService;

    public MainPresenter getMainPresenter() {
        if (mainPresenter == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                // loader.load(getClass().getResourceAsStream("MainNew.fxml"));
                loader.load(getClass().getResourceAsStream("MainNew.fxml"));
                mainPresenter = (MainPresenter) loader.getController();
                //mainPresenter.setCustomersPresenter(getCustomersPresenter());
                mainPresenter.setDetailsPresenter(getDetailsPresenter());
                mainPresenter.setInvoicesPresenter(getInvoicesPresenter());
                mainPresenter.setBillManagementPresenter(getBillManagementPresenter());
                mainPresenter.setMessagePresenter(getMessagePresenter());
                mainPresenter.setFilterCustomerPresenter(getFilterCustomerPresenter());
            } catch (IOException e) {
                throw new RuntimeException("Unable to load MainNew.fxml", e);
            }
        }
        return mainPresenter;
    }

    private CustomersPresenter getCustomersPresenter() {
        if (customersPresenter == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                // loader.load(getClass().getResourceAsStream("/fxml/Customers.fxml"));
                loader.load(getClass().getResourceAsStream("Customers.fxml"));
                customersPresenter = (CustomersPresenter) loader.getController();
                customersPresenter.setMainPresenter(getMainPresenter());
                customersPresenter.setDetailsPresenter(getDetailsPresenter());
                customersPresenter.setMessagesPresenter(getMessagePresenter());
            } catch (IOException e) {
                throw new RuntimeException("Unable to load Customers.fxml", e);
            }
        }
        return customersPresenter;
    }

    public DetailsPresenter getDetailsPresenter() {
        if (detailsPresenter == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("DetailsTab.fxml"));
                detailsPresenter = (DetailsPresenter) loader.getController();
                detailsPresenter.setMainPresenter(getMainPresenter());
                detailsPresenter.setCustomerPresenter(getCustomersPresenter());
                detailsPresenter.setPackageService(getPackageService());
            } catch (IOException e) {
                throw new RuntimeException("Unable to load DetailsTab.fxml", e);
            }
        }
        return detailsPresenter;
    }

    public InvoicesPresenter getInvoicesPresenter() {
        if (invoicesPresenter == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("Invoices.fxml"));
                invoicesPresenter = (InvoicesPresenter) loader.getController();
                invoicesPresenter.setMainPresenter(getMainPresenter());
            } catch (IOException e) {
                throw new RuntimeException("Unable to load Invoices.fxml", e);
            }

        }
        return invoicesPresenter;
    }

    private BillingManagementPresenter getBillManagementPresenter() {
        if (billManagementPresenter == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("BillingManagement.fxml"));
                billManagementPresenter = (BillingManagementPresenter) loader.getController();
                billManagementPresenter.setMainPresenter(getMainPresenter());
                billManagementPresenter.setInvoicePresenter(getInvoicesPresenter());
            } catch (IOException e) {
                throw new RuntimeException("Unable to load BillingManagemanet.fxml", e);
            }

        }
        return billManagementPresenter;
    }

    private MessagesPresenter getMessagePresenter() {
        if (messagePresenter == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("Messages.fxml"));
                messagePresenter = (MessagesPresenter) loader.getController();
                messagePresenter.setMainPresenter(getMainPresenter());
            } catch (IOException e) {
                throw new RuntimeException("Unable to load Messages.fxml", e);
            }

        }
        return messagePresenter;
    }

    public FilterCustomersPresenter getFilterCustomerPresenter() {

        if (filterCustomerPresenter == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("FilterCustomersTab.fxml"));
                filterCustomerPresenter = (FilterCustomersPresenter) loader.getController();
                filterCustomerPresenter.setMainPresenter(getMainPresenter());
            } catch (IOException e) {
                throw new RuntimeException("Unable to load FilterCustomersTab.fxml", e);
            }

        }
        return filterCustomerPresenter;
    }

    public CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public PackageService getPackageService() {
        if (packageService == null) {
            packageService = new PackageService();

        }
        return packageService;
    }

    public ProductService getProductService() {
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
    }
    */

}
