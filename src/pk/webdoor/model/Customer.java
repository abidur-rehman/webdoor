/**
 *
 */
package pk.webdoor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Abid.Ur-Rehman
 *
 */
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "select c from Customer c"),
    @NamedQuery(name = "Customer.countAll", query = "select count(c) from Customer c")})
@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable, ICustomer, Comparable<Customer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_ID")
    private Integer cid;

    @Column(name = "CKEY", unique = true, nullable = false)
    private Integer ckey;

    @Column(name = "CNIC", unique = true, nullable = false)
    private String cnic;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "FATHERNAME", nullable = false)
    private String fathername;

    @Column(name = "USERNAME", nullable = true)
    private String username;

    @Column(name = "PASSWORD", nullable = true)
    private String password;

    @Column(name = "ZONE", nullable = true)
    private String zone;

    @Column(name = "DOR", nullable = true)
    private String dor;

    @Column(name = "TELEPHONE", nullable = true)
    private String telephone;

    @Column(name = "MOBILE", nullable = true)
    private String mobile;

    @Column(name = "EMAIL", nullable = true)
    private String email;

    @Column(name = "ADDRESS", nullable = true)
    private String address;
    
    @Transient
    private String customerPackage;

    @Column(name = "COMMENTS", nullable = true)
    private String comments;

    /*
     @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
     private Set<WDPackage> wdpackages = new HashSet<WDPackage>(0);
	
     public Set<WDPackage> getWdpackages() {
     return wdpackages;
     }
	
     public void setWdpackages(Set<WDPackage> wdpackages) {
     this.wdpackages = wdpackages;
     }
     */
    /*
     * Version with returing list threw exception while loading
     */
    /*	
     @ManyToMany(cascade = CascadeType.ALL)
     @JoinTable(name = "PACKAGETOCUST", joinColumns = { 
     @JoinColumn(name = "CUSTOMER_ID") }, inverseJoinColumns = { @JoinColumn(name =
     "WDPACKAGE_ID") })		
     private List<WDPackage> wdpackages = new ArrayList<WDPackage>(0);
	
     public List<WDPackage> getWdpackages() {
     return wdpackages;
     }
	
     public void setWdpackages(List<WDPackage> wdpackages) {
     this.wdpackages = wdpackages;
     }
     */
    /*
     @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     @JoinTable(name = "PACKAGETOCUST", joinColumns = { 
     @JoinColumn(name = "CUSTOMER_ID") }, inverseJoinColumns = { @JoinColumn(name =
     "WDPACKAGE_ID") })
     @Sort(type = SortType.NATURAL) 
     private SortedSet<WDPackage> wdpackages = new TreeSet<WDPackage>();
	
     public SortedSet<WDPackage> getWdpackages() {
     return wdpackages;
     }
	
     public void setWdpackages(SortedSet<WDPackage> wdpackages) {
     this.wdpackages = wdpackages;
     }
     */
    /*
     * Not working
     * 
     @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     @JoinTable(name = "PACKAGETOCUST", joinColumns = { 
     @JoinColumn(name = "CUSTOMER_ID") }, inverseJoinColumns = { @JoinColumn(name =
     "WDPACKAGE_ID") })
     private LinkedHashSet<WDPackage> wdpackages = new LinkedHashSet<WDPackage>(0);
	
     public LinkedHashSet<WDPackage> getWdpackages() {
     return wdpackages;
     }
	
     public void setWdpackages(LinkedHashSet<WDPackage> wdpackages) {
     this.wdpackages = wdpackages;
     }
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "PACKAGETOCUST", joinColumns = {
        @JoinColumn(name = "CUSTOMER_ID")}, inverseJoinColumns = {
        @JoinColumn(name
                = "WDPACKAGE_ID")})
    private Set<WDPackage> wdpackages = new HashSet<WDPackage>(0);

    public Set<WDPackage> getWdpackages() {
        return wdpackages;
    }

    public void setWdpackages(Set<WDPackage> wdpackages) {
        this.wdpackages = wdpackages;
    }

    /*
     * Version with returing list threw exception while loading
     */
    @OneToMany(cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    //@JoinColumn(name="CUSTOMER_ID")
    private List<InvoiceToCust> invoices = new ArrayList<InvoiceToCust>(0);

    public List<InvoiceToCust> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceToCust> invoices) {
        this.invoices = invoices;
    }


    /*	
     @OneToMany(cascade = {CascadeType.ALL},
     fetch = FetchType.EAGER)
     @JoinColumn(name="CUSTOMER_ID")
     private Set<InvoiceToCust> invoices = new HashSet<InvoiceToCust>();
	
     public Set<InvoiceToCust> getInvoices() {
     return invoices;
     }

     public void setInvoices(Set<InvoiceToCust> invoices) {
     this.invoices = invoices;
     }
     */
    public Integer getCid() {
        if (cid == null) {
            return null;
        } else {
            return cid;
        }
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getCkey() {
        if (ckey == null) {
            return null;
        } else {
            return ckey;
        }
    }

    public void setCkey(Integer ckey) {
        this.ckey = ckey;
    }

    public String getCnic() {
        if (cnic == null) {
            return "";
        } else {
            return cnic;
        }
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getName() {
        if (name == null) {
            return "";
        } else {
            return name;
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathername() {
        if (fathername == null) {
            return "";
        } else {
            return fathername;
        }
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getUsername() {
        if (username == null) {
            return "";
        } else {
            return username;
        }        
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        if (password == null) {
            return "";
        } else {
            return password;
        }          
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZone() {
        if (zone == null) {
            return "";
        } else {
            return zone;
        }        
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDor() {
        if (dor == null) {
            return "";
        } else {
            return dor;
        }        
    }

    public void setDor(String dor) {
        this.dor = dor;
    }

    public String getTelephone() {
        if (telephone == null) {
            return "";
        } else {
            return telephone;
        }        
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        if (address == null) {
            return "";
        } else {
            return address;
        }        
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        if (mobile == null) {
            return "";
        } else {
            return mobile;
        }        
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        if (email == null) {
            return "";
        } else {
            return email;
        }        
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerPackage() {
        return customerPackage;
    }

    public void setCustomerPackage(String customerPackage) {
        this.customerPackage = customerPackage;
    }
    
    public String getComments() {
        if (comments == null) {
            return "";
        } else {
            return comments;
        }        
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public int compareTo(Customer o) {
        String name = this.getName().toUpperCase();
        String otherName = o.getName().toUpperCase();
        return name.compareTo(otherName);
    }

    public Customer(String name, String fathername, String username, String password, String zone, String dor) {
        this.fathername = fathername;
        this.username = username;
        this.password = password;
        this.zone = zone;
        this.dor = dor;
    }
    
    public Customer(){}

}
