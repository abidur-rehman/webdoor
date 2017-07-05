package pk.webdoor.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


@NamedQueries({
	@NamedQuery(name = "Product.findAll", query = "select p from Product p"),
	@NamedQuery(name = "Product.findByMonth", query = "from Product where MONTH=?"),
	@NamedQuery(name = "Product.countAll", query = "select count(p) from Product p") })
@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_ID")
	private Integer prodid;
	
	@Column(name = "MONTH", nullable = false)
	private String month;

	@Column(name = "ISSUEDATE", unique = true, nullable = false)
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date issuedate;
	
	@Column(name = "DUEDATE", nullable = false)
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date duedate;
	
	@Column(name = "PAYMENT", nullable = false)
	private Integer payment;
	
	@Column(name = "FINE", nullable = false)
	private Integer fine;

	public Integer getProdid() {
		return prodid;
	}

	public void setProdid(Integer prodid) {
		this.prodid = prodid;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	/*
	public Date getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(Date issuedate) {
		this.issuedate = issuedate;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
*/
	
	public String getIssuedate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return simpleDateFormat.format(issuedate);
	}

	public void setIssuedate(String issuedate) throws ParseException {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.issuedate = simpleDateFormat.parse(issuedate);
	}

	public String getDuedate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return simpleDateFormat.format(duedate);
	}

	public void setDuedate(String duedate) throws ParseException {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.duedate = simpleDateFormat.parse(duedate);
	}
	
	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Integer getFine() {
		return fine;
	}

	public void setFine(Integer fine) {
		this.fine = fine;
	}

   /*
	public boolean equals(Object obj) {
		String month = this.getMonth();
		String otherMonth = ((Product) obj).getMonth();
		if (month.equals(otherMonth)){
			return true;
			}
	    else {
	    	return false;
	    	}
	}
	*/
	
}
