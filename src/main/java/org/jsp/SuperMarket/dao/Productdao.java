package org.jsp.SuperMarket.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.SuperMarket.dto.Product;
import org.jsp.SuperMarket.repository.ProductRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Productdao {
	
@Autowired
ProductRepositary productRepositary;

	public Product save(Product product) {
		
		return productRepositary.save(product);
	}

	public Product find(int p_id) {
		Optional<Product> optional=productRepositary.findById(p_id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

	public void deleteproduct(int p_id) {
		productRepositary.deleteById(p_id);
		
	}

	public List<Product> findAll() {
		
		return productRepositary.findAll();
	}

	public List<Product> fetchCustomerProduct() {
		
		return productRepositary.findByStatus(true);
	}
	
	public Product find(String name)
	{
		return productRepositary.findByPname(name);
	}
	
	

}
