package org.jsp.SuperMarket.dto;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
@Component
public class Customer {
	@Id
	@GeneratedValue(generator = "customerid")
	@GenericGenerator(name="customerid",strategy = "org.jsp.SuperMarket.helper.CustomerId")
	String cust_id;
	String cust_name;
	long cust_mobile;
	String password;
	String cust_email;
	String address;
	boolean status;
	double wallet;
	int otp;

	@OneToOne(cascade = CascadeType.ALL)
	Cart cart;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<ShoppingOrders> orders;
}
