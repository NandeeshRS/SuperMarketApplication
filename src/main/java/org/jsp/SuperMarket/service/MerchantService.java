package org.jsp.SuperMarket.service;

import java.util.List;
import java.util.Random;

import org.jsp.SuperMarket.dao.Merchantdao;
import org.jsp.SuperMarket.dao.Productdao;
import org.jsp.SuperMarket.dto.Merchant;
import org.jsp.SuperMarket.dto.Product;
import org.jsp.SuperMarket.exception.AllException;
import org.jsp.SuperMarket.helper.Login;
import org.jsp.SuperMarket.helper.ResponseStructure;
import org.jsp.SuperMarket.helper.VerificationEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {
	
	@Autowired
	VerificationEmailSender emailSender;
	
	@Autowired
	Merchantdao merchantdao;
	
	@Autowired
	Productdao productdao;

	public ResponseStructure<Merchant> signup(Merchant merchant) {
		ResponseStructure<Merchant> structure=new ResponseStructure<>();
		
		merchant.setOtp(new Random().nextInt(100000,999999));
		emailSender.sendEmail(merchant);
		
		structure.setMessage("Verification Mail Sent Verify OTP to Create Account");
		structure.setStatuscode(HttpStatus.PROCESSING.value());
		structure.setData(merchantdao.save(merchant));
		
		return structure;
		
	}


	public ResponseStructure<Merchant> verify(String id, int otp) throws AllException {
		ResponseStructure<Merchant> structure=new ResponseStructure<>();
		Merchant merchant=merchantdao.find(id);
		if(merchant.getOtp()==otp) {
			merchant.setStatus(true);
			structure.setMessage("Account Created Successfully");
			structure.setStatuscode(HttpStatus.CREATED.value());
			structure.setData(merchantdao.save(merchant));
		}else {
			throw new AllException("otp mismatch");
		}
		
		return structure;
	}


	public ResponseStructure<Merchant> login(Login login) throws AllException {
		ResponseStructure<Merchant> structure=new ResponseStructure<>();
		Merchant merchant=merchantdao.find(login.getId());
		if(merchant==null) {
			throw new AllException("Invalid Id");
		}else {
			if(merchant.getPassword().equals(login.getPassword())) {
				if(merchant.isStatus()) {
				structure.setData(merchant);
				structure.setMessage("Login Successfull");
				structure.setStatuscode(HttpStatus.ACCEPTED.value());
				}else {
					throw new AllException("Verify OTP first");
				}
			}else {
				throw new AllException("Invalid Password");
			}
		}
				return structure;
	}
	
	public ResponseStructure<Merchant> addproduct(Product product, String mid) {
		ResponseStructure<Merchant> structure=new ResponseStructure<>();
		Merchant merchant=merchantdao.find(mid);
		
		product.setMerchant(merchant);
		
		product.setPname(merchant.getName()+"-"+ product.getPname());
		
		List<Product> list=merchant.getProducts();
		list.add(product);
		
		structure.setMessage("Product added successfully");
		structure.setData(merchantdao.save(merchant));
		structure.setStatuscode(HttpStatus.CREATED.value());
		
		
		return structure;
	}


	public ResponseStructure<List<Product>> fetchproduct(String mid) throws AllException {
		ResponseStructure<List<Product>> structure=new ResponseStructure<>();
		Merchant  merchant=merchantdao.find(mid);
		if(merchant.getProducts().isEmpty()) {
			throw new AllException("No Products Found");
		}else {
			structure.setMessage("Data Found");
			structure.setStatuscode(HttpStatus.FOUND.value());
			structure.setData(merchant.getProducts());
			return structure;
		}
	}

	public ResponseStructure<Product> updateproduct(Product product, String mid) {
		ResponseStructure<Product> structure=new ResponseStructure<>();
		Merchant  merchant=merchantdao.find(mid);
		
		product.setMerchant(merchant);
		
		structure.setData(productdao.save(product));
		structure.setMessage("Updated Successfully");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		
		return structure;
	}


	public ResponseStructure<Product> delete(String mid, int p_id) {
		
		Merchant merchant=merchantdao.find(mid);
		Product product=productdao.find(p_id);
		
		merchant.getProducts().remove(product);
		merchantdao.save(merchant);
		
		productdao.deleteproduct(p_id);
		
		ResponseStructure<Product> structure=new ResponseStructure<>();
		structure.setMessage("Data Deleted Successfully");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		structure.setData(product);
		
		
		
		return structure;
	}


	

}
