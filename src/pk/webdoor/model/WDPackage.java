/**
 * 
 */
package pk.webdoor.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Abid.Ur-Rehman
 *
 */
@NamedQueries({
	@NamedQuery(name = "WDPackage.findAll", query = "select p from WDPackage p"),
        @NamedQuery(name = "WDPackage.findByName", query = "from WDPackage where PACKAGENAME=?"),
	@NamedQuery(name = "WDPackage.findByDetails", query = "from WDPackage where PACKAGEDETAILS=?"), })
@Entity
@Table(name = "WDPACKAGE")
public class WDPackage implements Serializable{
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WDPACKAGE_ID")
	private Integer pid;
	
	@Column(name = "PACKAGENAME", nullable = true)
	private String packageName;
	
	/*
	@Column(name = "DORNAME", nullable = false)
	private String dorName;
	*/
	
	@Column(name = "PACKAGEDETAILS", nullable = true)
	private Integer packageDetails;
	
	@Column(name = "CREATED", nullable = true)
	private Date created;
	
	/*
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, targetEntity = Customer.class)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	*/
	
	public Integer getPid() {
		return pid;
	}
	
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	/*
	public String getDorName() {
		return dorName;
	}
	
	public void setDorName(String dorName) {
		this.dorName = dorName;
	}*/
	
	public Integer getPackageDetails() {
		return packageDetails;
	}
	
	public void setPackageDetails(Integer packageDetails) {
		this.packageDetails = packageDetails;
	}

	public String getCreated() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD-MM-YYYY");
		return simpleDateFormat.format(created);
	}
	
	public void setCreated(String created) throws ParseException {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD-MM-YYYY");
		this.created = simpleDateFormat.parse(created);
	}


	
}
