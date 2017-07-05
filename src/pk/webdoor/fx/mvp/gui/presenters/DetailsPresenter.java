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
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import pk.webdoor.fx.exceptions.CustomerUpdateException;
import pk.webdoor.fx.service.CustomerService;
import pk.webdoor.fx.service.PackageService;
import pk.webdoor.model.Customer;
import pk.webdoor.model.ICustomer;
import pk.webdoor.model.WDPackage;

public class DetailsPresenter implements Initializable {

    private static final Logger log = Logger.getLogger(DetailsPresenter.class.getName());
    @FXML
    private Node root;
    @FXML
    TextField cid;
    @FXML
    TextField ckey;
    @FXML
    TextField cnic;
    @FXML
    TextField name;
    @FXML
    TextField fathername;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    TextField zone;
    @FXML
    TextField dor;
    @FXML
    TextField telephone;
    @FXML
    TextField mobile;
    @FXML
    TextField email;
    @FXML
    TextField address;
    @FXML
    TextField customerPackage;
    @FXML
    ChoiceBox<String> packagesList;
    @FXML
    TextArea comments;

    private ObservableList<String> packagesContent;

    @Inject
    private MainPresenter mainPresenter;

    @Inject
    private CustomersPresenter customersPresenter;

    @Inject
    private CustomerService customerService;

    @Inject
    private PackageService packageService;

    private Customer selectedCustomer;

    private Set<WDPackage> customerPackagesSet;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateDetails();
        configureCustomerDetails();

    }

    public Node getView() {
        return root;
    }

    /**
     * This function prepares the choise box for package selection It is called
     * each time when a customer changes.
     *
     */
    private void configurePackages() {
        List<String> packageNames = new ArrayList<>();
        Set<WDPackage> allpackages = new HashSet<>(packageService.getPackages());
        for (WDPackage p : allpackages) {
            packageNames.add(p.getPackageName());
        }
        packagesContent = FXCollections.observableList((List<String>) packageNames);
    }

    //private WDPackage selectedPackage;
    private void updateDetails() {

        final Customer customer = getSelectedCustomer();

        if (customer != null) {

            cid.setText(String.valueOf(customer.getCid()));
            ckey.setText(String.valueOf(customer.getCkey()));
            cnic.setText(customer.getCnic());
            name.setText(customer.getName());
            fathername.setText(customer.getFathername());
            username.setText(customer.getUsername());
            password.setText(customer.getPassword());
            zone.setText(customer.getZone());
            dor.setText(customer.getDor());
            telephone.setText(customer.getTelephone());
            mobile.setText(customer.getMobile());
            email.setText(customer.getEmail());
            address.setText(customer.getAddress());
            comments.setText(customer.getComments());

            customerPackagesSet = customer.getWdpackages();
            /*
             if (customerPackagesSet.isEmpty()) {
             customerPackagesSet = new HashSet<>(packageService.getPackages());
             }
             */

            if (customerPackagesSet.isEmpty()) {
                customerPackage.setText("No Package");
            } else {
                WDPackage firtPackageInSet = customerPackagesSet.iterator().next();
                customerPackage.setText(firtPackageInSet.getPackageName());
            }

            configurePackages();
            packagesList.setItems(packagesContent);
            //packagesList.getSelectionModel().selectFirst();

            packagesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
                    if (newValue != null) {
                        customerPackage.setText(newValue);
                        updateSaveCustomerButtonState();
                    }
                }
            });

            /**
             * Currently only one package is selected and showed in printout
             * this needs to be changed.
             */
            //selectedPackage = packSet.iterator().next();
            /*
             List<String> packageNames = new ArrayList<>();
             Set<WDPackage> allpackages = new HashSet<>(packageService.getPackages());
             for (WDPackage p : allpackages) {
             System.out.println("package ID: " + p.getPid()
             + " package details : " + p.getPackageDetails());
             packageNames.add(p.getPackageName());
             }
             */

            /*
             * Example with ArrayList: Currently not working
             * 
             * 
             * List<Integer> packageDetails = new ArrayList<Integer>();
             * List<WDPackage> packages = (List<WDPackage>)
             * customer.getWdpackages();
             * 
             * 
             * if(packages.isEmpty()) {
             * 
             * System.out.println("pacages are null therefore calling service");
             * packages = packageService.getPackages(); }
             * 
             * for(WDPackage p: packages){
             * packageDetails.add(p.getPackageDetails()); }
             */

            /*
             * Example with SortedTree
             * 
             * SortedSet<WDPackage> packSet = customer.getWdpackages();
             * 
             * 
             * System.out.println("packages " + packSet);
             * 
             * if(packSet.isEmpty()){ packSet = new
             * TreeSet<WDPackage>(packageService.getPackages()); }
             * 
             * Iterator<WDPackage> it=packSet.iterator();
             * 
             * while(it.hasNext()) { WDPackage value=(WDPackage)it.next();
             * 
             * System.out.println("package :"+value.getPackageDetails()); }
             * 
             * for(WDPackage p: packSet){ System.out.println("package ID: " +
             * p.getPid() + " package details : " + p.getPackageDetails());
             * packageDetails.add(p.getPackageDetails()); }
             */

            /*
             * Example with LinkedHashSet
             * 
             * LinkedHashSet<WDPackage> packSet = customer.getWdpackages();
             * 
             * 
             * System.out.println("packages " + packSet);
             * 
             * if(packSet.isEmpty()){ packSet = new
             * LinkedHashSet<WDPackage>(packageService.getPackages()); }
             * 
             * Iterator<WDPackage> it=packSet.iterator();
             * 
             * while(it.hasNext()) { WDPackage value=(WDPackage)it.next();
             * 
             * System.out.println("package :"+value.getPackageDetails()); }
             * 
             * for(WDPackage p: packSet){ System.out.println("package ID: " +
             * p.getPid() + " package details : " + p.getPackageDetails());
             * packageDetails.add(p.getPackageDetails()); }
             */
            //packagesList.getSelectionModel().selectFirst();
        } else {
            cid.setText("");
            ckey.setText("");
            cnic.setText("");
            name.setText("");
            fathername.setText("");
            username.setText("");
            password.setText("");
            zone.setText("");
            dor.setText("");
            telephone.setText("");
            mobile.setText("");
            email.setText("");
            address.setText("");
            packagesList.getSelectionModel().clearSelection();
            comments.setText("");
        }
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        updateDetails();
        updateSaveCustomerButtonState();
    }

    private void configureCustomerDetails() {
        if (root != null) {
            root.setVisible(false);
        }

        if (root != null) {
            root.addEventFilter(EventType.ROOT, new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    if (event.getEventType() == MouseEvent.MOUSE_RELEASED
                            || event.getEventType() == KeyEvent.KEY_RELEASED) {
                        // updateDetails();
                        updateSaveCustomerButtonState();
                    }
                }
            });
        }
    }

    public void updateSaveCustomerButtonState() {
        boolean disable = true;
        if (mainPresenter.saveCustomer != null
                && customersPresenter.getTable() != null) {
            final boolean nothingSelected = customersPresenter.getTable().getSelectionModel().getSelectedItems().isEmpty();
            disable = nothingSelected;
        }

        if (disable == false) {

            /**
             * Customer selected
             */
            final Customer selectedCustomer = getSelectedCustomer();

            // System.out.println("selectedCustomer :" +
            // selectedCustomer.getName());
            /**
             * DetailsData customer collected from gui
             */
            final DetailsData d = new DetailsData();

            //Customer formCustomer = getFormCustomer();
            //System.out.println("formCustomer :" + formCustomer.getName());
            SaveState s = computeSaveState(d, selectedCustomer);

            disable = s != SaveState.UNSAVED;

        }

        if (mainPresenter.saveCustomer != null) {
            mainPresenter.saveCustomer.setDisable(disable);
        }
    }

    private final class DetailsData implements ICustomer {

        @Override
        public Integer getCid() {
            if (cid == null || isEmpty(cid.getText())) {
                return null;
            }
            return Integer.parseInt(cid.getText());
        }

        @Override
        public Integer getCkey() {
            if (ckey == null || isEmpty(ckey.getText())) {
                return 0;
            }
            return Integer.parseInt(ckey.getText());
        }

        @Override
        public String getCnic() {
            if (cnic == null || isEmpty(cnic.getText())) {
                return "";
            }
            return cnic.getText();
        }

        @Override
        public String getName() {
            if (name == null || isEmpty(name.getText())) {
                return "";
            }
            return name.getText();
        }

        @Override
        public String getFathername() {
            if (fathername == null || isEmpty(fathername.getText())) {
                return "";
            }
            return fathername.getText();
        }

        @Override
        public String getUsername() {
            if (username == null || isEmpty(username.getText())) {
                return "";
            }
            return username.getText();
        }

        @Override
        public String getPassword() {
            if (password == null || isEmpty(password.getText())) {
                return "";
            }
            return password.getText();
        }

        @Override
        public String getZone() {
            if (zone == null || isEmpty(zone.getText())) {
                return "";
            }
            return zone.getText();
        }

        @Override
        public String getDor() {
            if (dor == null || isEmpty(dor.getText())) {
                return "";
            }
            return dor.getText();
        }

        @Override
        public String getTelephone() {
            if (telephone == null || isEmpty(telephone.getText())) {
                return "";
            }
            return telephone.getText();
        }

        @Override
        public String getMobile() {
            if (mobile == null || isEmpty(mobile.getText())) {
                return "";
            }
            return mobile.getText();
        }

        @Override
        public String getEmail() {
            if (email == null || isEmpty(email.getText())) {
                return "";
            }
            return email.getText();
        }

        @Override
        public String getAddress() {
            if (address == null || isEmpty(address.getText())) {
                return "";
            }
            return address.getText();
        }

        @Override
        public String getCustomerPackage() {
            if (customerPackage == null || isEmpty(customerPackage.getText())) {
                return "";
            }
            return customerPackage.getText();
        }

        @Override
        public String getComments() {
            if (comments == null || isEmpty(comments.getText())) {
                return "";
            }
            return comments.getText();
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

    private boolean isVoid(Object o) {
        if (o instanceof String) {
            return isEmpty((String) o);
        } else {
            return o == null;
        }
    }

    private static enum SaveState {

        INVALID, UNSAVED, UNCHANGED
    }

    private SaveState computeSaveState(ICustomer editedCustomer,
            Customer selectedCustomer) {
        /*
         System.out.println(edited.getCid() + " " + edited.getName() + " "
         + edited.getFathername() + " " + edited.getUsername() + " "
         + edited.getPassword() + edited.getTelephone() + " "
         + edited.getMobile() + " " + edited.getZone() + " "
         + edited.getAddress());

         System.out.println(selectedCustomer.getName() + " "
         + selectedCustomer.getFathername() + " "
         + selectedCustomer.getUsername() + " "
         + selectedCustomer.getPassword()
         + selectedCustomer.getTelephone() + " "
         + selectedCustomer.getMobile() + " "
         + selectedCustomer.getZone() + " "
         + selectedCustomer.getAddress());
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

            if (!equal(editedCustomer.getCkey(), selectedCustomer.getCkey())) {
                return SaveState.UNSAVED;
            }

            if (!equal(editedCustomer.getCnic(), selectedCustomer.getCnic())) {
                return SaveState.UNSAVED;
            }

            if (!equal(editedCustomer.getName(), selectedCustomer.getName())) {
                return SaveState.UNSAVED;
            }
            if (!equal(editedCustomer.getFathername(), selectedCustomer.getFathername())) {
                return SaveState.UNSAVED;
            }

            if (!equal(editedCustomer.getUsername(), selectedCustomer.getUsername())) {
                return SaveState.UNSAVED;
            }
            if (!equal(editedCustomer.getPassword(), selectedCustomer.getPassword())) {
                return SaveState.UNSAVED;
            }
            if (!equal(editedCustomer.getZone(), selectedCustomer.getZone())) {
                return SaveState.UNSAVED;
            }
            if (!equal(editedCustomer.getDor(), selectedCustomer.getDor())) {
                return SaveState.UNSAVED;
            }
            if (!equal(editedCustomer.getTelephone(), selectedCustomer.getTelephone())) {
                return SaveState.UNSAVED;
            }

            if (!equal(editedCustomer.getMobile(), selectedCustomer.getMobile())) {
                return SaveState.UNSAVED;
            }

            if (!equal(editedCustomer.getAddress(), selectedCustomer.getAddress())) {
                return SaveState.UNSAVED;
            }

            /*
             if (!equal(editedCustomer.getCustomerPackage(), newSelectedPackage)) {
             System.out.println("Customer Package is not same: " + editedCustomer.getCustomerPackage() + " " + newSelectedPackage);
             return SaveState.UNSAVED;
             }
             */
            /**
             * Packages of selected customer are compared with the package
             * (string) entered by user in gui
             */
            if (!comparePackages(editedCustomer.getCustomerPackage())) {
                return SaveState.UNSAVED;
            }

            if (!equal(editedCustomer.getComments(), selectedCustomer.getComments())) {
                return SaveState.UNSAVED;
            }

        } catch (Exception x) {
            // If there's an exception, some fields are invalid.
            return SaveState.INVALID;
        }
        // No field is invalid, no field needs saving.
        return SaveState.UNCHANGED;
    }

    /**
     * First case is when user entered something is GUI, it will be compared
     * with the original package.
     *
     * If there is "No Package" or null in GUI package field nothing will happen
     * but if something is entered in package then it will be saved.
     *
     * @param editedPackage
     * @return boolean
     */
    private boolean comparePackages(String editedPackage) {
        boolean flag = true;
        if (!customerPackagesSet.isEmpty()) {
            WDPackage firtPackageInSet = customerPackagesSet.iterator().next();
            if (!editedPackage.equals(firtPackageInSet.getPackageName())) {
                flag = false;
            }
        } else if ((editedPackage != null) && (!editedPackage.equals("No Package"))) {
            flag = false;
        }
        return flag;
    }

    private Customer getFormCustomer() {
        Customer formCustomer = new Customer();
        formCustomer.setCkey(Integer.parseInt(ckey.getText()));
        formCustomer.setName(name.getText());
        formCustomer.setFathername(fathername.getText());
        formCustomer.setUsername(username.getText());
        formCustomer.setPassword(password.getText());
        formCustomer.setZone(zone.getText());
        formCustomer.setDor(dor.getText());
        formCustomer.setTelephone(telephone.getText());
        formCustomer.setMobile(mobile.getText());
        formCustomer.setAddress(address.getText());
        String formPackage = packagesList.getSelectionModel().getSelectedItem().toString();
        formCustomer.setCustomerPackage(formPackage);
        formCustomer.setComments(comments.getText());
        /**
         * As customer does not have string pakage variable, therefore instead
         * of storing user selected package in customers set, a this.formPackage
         * variable is used to store the form package (new)
         */

        //WDPackage p = packageService.getPackageByDetails((Integer) packagesList.getSelectionModel().getSelectedItem());
        // List<WDPackage> list = new ArrayList<WDPackage>();
        // list.add(p);

        /*
         * LinkedHashSet<WDPackage> set = new LinkedHashSet<WDPackage>();
         * updateCustomer.setWdpackages(set);
         */

        /*
         * List<WDPackage> list = new ArrayList<WDPackage>();
         * updateCustomer.setWdpackages(list);
         */
        /*
         Set<WDPackage> set = new HashSet<>();
         set.add(p);
         formCustomer.setWdpackages(set);
         */
        return formCustomer;
    }

    public void updateCustomerDetails_OLD_Version() {
        Customer updateCustomer = getSelectedCustomer();
        updateCustomer.setCkey(Integer.parseInt(ckey.getText()));
        updateCustomer.setName(name.getText());
        updateCustomer.setCnic(cnic.getText());
        updateCustomer.setFathername(fathername.getText());
        updateCustomer.setUsername(username.getText());
        updateCustomer.setPassword(password.getText());
        updateCustomer.setZone(zone.getText());
        updateCustomer.setDor(dor.getText());
        updateCustomer.setTelephone(telephone.getText());
        updateCustomer.setMobile(mobile.getText());
        updateCustomer.setAddress(address.getText());
        updateCustomer.setComments(comments.getText());

        WDPackage p = packageService.getPackageByName(customerPackage.getText());
        //WDPackage p = packageService.getPackageByName(packagesList.getSelectionModel().getSelectedItem());
        // List<WDPackage> list = new ArrayList<WDPackage>();
        // list.add(p);

        /*
         * LinkedHashSet<WDPackage> set = new LinkedHashSet<WDPackage>();
         * updateCustomer.setWdpackages(set);
         */

        /*
         * List<WDPackage> list = new ArrayList<WDPackage>();
         * updateCustomer.setWdpackages(list);
         */
        Set<WDPackage> set = new HashSet<>();
        set.add(p);
        updateCustomer.setWdpackages(set);

        String updateResult;
        try {
            updateResult = customerService.updateCustomer(updateCustomer);
            if (updateResult.equals("FAILED")) {
                throw new CustomerUpdateException(updateResult);
            } else {
                mainPresenter.displayMessage("Customer updated Successfully");
                customersPresenter.refreshAfterSave();
            }
        } catch (CustomerUpdateException e) {
            System.out.println(e);
            // displayMessage("Exception " + e);
        }

    }

    public void updateCustomerDetails() {
        log.debug("Invoking updateCustomerDetailsWithTask....");
        final Customer updateCustomer = getSelectedCustomer();
        updateCustomer.setCkey(Integer.parseInt(ckey.getText()));
        updateCustomer.setName(name.getText());
        updateCustomer.setCnic(cnic.getText());
        updateCustomer.setFathername(fathername.getText());
        updateCustomer.setUsername(username.getText());
        updateCustomer.setPassword(password.getText());
        updateCustomer.setZone(zone.getText());
        updateCustomer.setDor(dor.getText());
        updateCustomer.setTelephone(telephone.getText());
        updateCustomer.setMobile(mobile.getText());
        updateCustomer.setAddress(address.getText());
        updateCustomer.setComments(comments.getText());

        WDPackage p = packageService.getPackageByName(customerPackage.getText());
        Set<WDPackage> set = new HashSet<>();
        set.add(p);
        updateCustomer.setWdpackages(set);

        log.debug("Updating customer:-" + updateCustomer.getCid() + " " + updateCustomer.getName());
        updateCustomerTask(updateCustomer);

    }

    private void updateCustomerTask(final Customer customer) {
        final Task<String> updateTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return customerService.mergeCustomer(customer);
            }
        };

        updateTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    if (!"SUCCESS".equals(updateTask.getValue())) {
                        //mainPresenter.displayErrorMessage("Customer update failed. Please Try again!");
                        //customersPresenter.showCustomersTable();
                        //setSelectedCustomer(getFormCustomer());
                        mainPresenter.displayErrorMessage(updateTask.getValue());
                    } else {
                        mainPresenter.displayMessage("Customer updated Successfully");
                        //customerPresenter.refreshAfterSave();
                        customersPresenter.showCustomersTable();
                    }
                }
            }
        });

        new Thread(updateTask).start();
    }
    
}
