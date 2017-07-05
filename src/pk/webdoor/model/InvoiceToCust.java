package pk.webdoor.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


@NamedQueries({
	@NamedQuery(name = "InvoiceToCust.findAll", query = "select i from InvoiceToCust i"),
	@NamedQuery(name = "InvoiceToCust.countAll", query = "select count(i) from InvoiceToCust i") })
@Entity
@Table(name = "INVOICETOCUST")
public class InvoiceToCust implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INTOCUST_ID")
	private Integer intocustid;
	
	@Column(name = "STATUS", nullable = false)
	private String status;

	@Column(name = "PAIDON", nullable = true)
	private Date paidon;
	
	@Column(name = "PAIDAMOUNT", nullable = true)
	private Integer paidamount;
	
	/*
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, targetEntity = Customer.class)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
   */
	
	/*
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", updatable = false, insertable = false)
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	*/
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	

	public Integer getIntocustid() {
		return intocustid;
	}

	public void setIntocustid(Integer intocustid) {
		this.intocustid = intocustid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
/*
	public Date getPaidon() {
		return paidon;
	}

	public void setPaidon(Date paidon) {
		this.paidon = paidon;
	}
*/
	public String getPaidon() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return simpleDateFormat.format(paidon);
	}

	public void setPaidon(String paidon) {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			this.paidon = simpleDateFormat.parse(paidon);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}	
	
	public Integer getPaidamount() {
		return paidamount;
	}

	public void setPaidamount(Integer paidamount) {
		this.paidamount = paidamount;
	}
	
	public InvoiceToCust(){}
	
}
