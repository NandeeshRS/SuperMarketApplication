package org.jsp.SuperMarket.dao;

import java.util.Optional;

import org.jsp.SuperMarket.dto.Customer;
import org.jsp.SuperMarket.dto.Merchant;
import org.jsp.SuperMarket.repository.CustomerRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Customerdao {
	@Autowired
	CustomerRepositary customerRepositary;

	public Customer save(Customer customer) {
		return customerRepositary.save(customer);
	}

	public Customer find(String id) {
		Optional<Customer> optional=customerRepositary.findById(id);
		if(optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
		
	}

}
