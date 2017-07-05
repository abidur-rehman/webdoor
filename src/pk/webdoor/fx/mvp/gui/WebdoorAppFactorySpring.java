/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.webdoor.fx.mvp.gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pk.webdoor.fx.dao.CustomerDaoImpl;
import pk.webdoor.fx.dao.ICustomerDao;
import pk.webdoor.fx.dao.IProductDao;
import pk.webdoor.fx.dao.IWDPackageDao;
import pk.webdoor.fx.dao.ProductDoaImpl;
import pk.webdoor.fx.dao.WDPackageDaoImpl;
import pk.webdoor.fx.mvp.gui.presenters.BillingManagementPresenter;
import pk.webdoor.fx.mvp.gui.presenters.CustomersPresenter;
import pk.webdoor.fx.mvp.gui.presenters.DetailsPresenter;
import pk.webdoor.fx.mvp.gui.presenters.FilterCustomersPresenter;
import pk.webdoor.fx.mvp.gui.presenters.InvoicesPresenter;
import pk.webdoor.fx.mvp.gui.presenters.MainPresenter;
import pk.webdoor.fx.mvp.gui.presenters.MonthDialogPresenter;
import pk.webdoor.fx.service.CustomerService;
import pk.webdoor.fx.service.PackageService;
import pk.webdoor.fx.service.ProductService;

/**
 *
 * @author Abid
 */
@Configuration
public class WebdoorAppFactorySpring {

    @Bean
    public MainPresenter mainPresenter() {
        return loadPresenter("/resources/fxml/MainNew.fxml");
    }

    @Bean
    public CustomersPresenter customersPresenter() {
        return loadPresenter("/resources/fxml/Customers.fxml");
    }

    @Bean
    public DetailsPresenter detailsPresenter() {
        return loadPresenter("/resources/fxml/DetailsTab.fxml");
    }

    @Bean
    public InvoicesPresenter invoicesPresenter() {
        return loadPresenter("/resources/fxml/Invoices.fxml");
    }

    @Bean
    public BillingManagementPresenter billManagementPresenter() {
        return loadPresenter("/resources/fxml/BillingManagement.fxml");
    }

    /**
     * Message Presenter is not used any more instead messagebar in main
     * presenter is used
     *
     */
    /*
     @Bean
     public MessagesPresenter messagePresenter() {
     return loadPresenter("Messages.fxml");
     }
     */
    @Bean
    public FilterCustomersPresenter filterCustomerPresenter() {
        return loadPresenter("/resources/fxml/FilterCustomersTab.fxml");
    }

    @Bean
    public MonthDialogPresenter monthDialogPresenter() {
        return loadPresenter("/resources/fxml/MonthDialog.fxml");
    }
    
    private <T> T loadPresenter(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.load(getClass().getResourceAsStream(fxmlFile));
            return (T) loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to load FXML file '%s'", fxmlFile), e);
        }
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

    @Bean
    public PackageService packageService() {
        return new PackageService();
    }

    @Bean
    public ProductService productService() {
        return new ProductService();
    }

    @Bean
    public ICustomerDao customerDao() {
        return new CustomerDaoImpl();
    }

    @Bean
    public IWDPackageDao packageDao() {
        return new WDPackageDaoImpl();
    }

    @Bean
    public IProductDao productDao() {
        return new ProductDoaImpl();
    }
}
