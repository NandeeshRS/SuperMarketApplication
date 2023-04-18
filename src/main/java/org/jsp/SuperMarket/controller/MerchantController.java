package org.jsp.SuperMarket.controller;

import java.util.List;

import org.jsp.SuperMarket.dto.Merchant;
import org.jsp.SuperMarket.dto.Product;
import org.jsp.SuperMarket.exception.AllException;
import org.jsp.SuperMarket.helper.Login;
import org.jsp.SuperMarket.helper.ResponseStructure;
import org.jsp.SuperMarket.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("merchant")
public class MerchantController {

//	while we are writing every where merchant we directly write in the above and then we can continue the url
//	@PostMapping("merchantsignup")
	
	
	@Autowired
	MerchantService merchantService;
	
	@PostMapping("signup")
	public ResponseStructure<Merchant> signup(@RequestBody Merchant merchant){
		return merchantService.signup(merchant);
	}
	
	@PutMapping("verify/{id}/{otp}")
	public ResponseStructure<Merchant> verify(@PathVariable String id,@PathVariable int otp) throws AllException{
		return merchantService.verify(id,otp);
	}
	
	@PostMapping("login")
	public ResponseStructure<Merchant> login(@RequestBody Login login) throws AllException{
		return merchantService.login(login);
	}
	
	@PostMapping("product/add/{mid}")
	public ResponseStructure<Merchant> saveproduct(@RequestBody Product product,@PathVariable String mid) throws AllException{
		return merchantService.addproduct(product,mid);
	}
	
	@GetMapping("product/view/{mid}")
	public ResponseStructure<List<Product>> fetchproduct(@PathVariable String mid) throws AllException{
		return merchantService.fetchproduct(mid);
	}
	
	@PutMapping("product/edit/{mid}")
	public ResponseStructure<Product> editproduct(@RequestBody Product product,@PathVariable String mid) throws AllException{
		return merchantService.updateproduct(product,mid);
	}
	
	@DeleteMapping("product/delete/{mid}/{p_id}")
	public ResponseStructure<Product> deleteproduct(@PathVariable String mid, @PathVariable int p_id){
		return merchantService.delete(mid,p_id);
	}
	
	
	
}
