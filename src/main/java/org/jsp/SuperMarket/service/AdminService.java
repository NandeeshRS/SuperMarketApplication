package org.jsp.SuperMarket.service;

import java.util.List;

import org.jsp.SuperMarket.dao.Productdao;
import org.jsp.SuperMarket.dto.Product;
import org.jsp.SuperMarket.exception.AllException;
import org.jsp.SuperMarket.helper.Login;
import org.jsp.SuperMarket.helper.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired
	Productdao productdao;

	public ResponseStructure<Login> login(Login login) throws AllException {
		ResponseStructure<Login> structure=new ResponseStructure<>() ;
	if(login.getId().equals("admin")) {
		if(login.getPassword().equals("admin")) {
			structure.setData(login);
			structure.setMessage("Login Success");
			structure.setStatuscode(HttpStatus.ACCEPTED.value());
			return structure;
		}else {
			throw new AllException("Invalid Password");
		}
	}else {
		throw new AllException("Invalid ID");
	}
		
	}

	public ResponseStructure<List<Product>> fetchproducts() throws AllException {
		ResponseStructure<List<Product>> structure=new ResponseStructure<>();
		List<Product> list=productdao.findAll();
		
		if(list.isEmpty()) {
			throw new AllException("No Products Found");
		}else {
		structure.setMessage("Product Found");
		structure.setData(list);
		structure.setStatuscode(HttpStatus.FOUND.value());
		return structure;
		}
	}

	public ResponseStructure<Product> changeStatus(int p_id) {
		ResponseStructure<Product> structure=new ResponseStructure<>();
		
		Product product=productdao.find(p_id);
		
		if(product.isStatus())
			product.setStatus(false);
		else
			product.setStatus(true);
		
		structure.setMessage("Changed Status Successfull");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		structure.setData(productdao.save(product));
		
		return structure;
	}

}
