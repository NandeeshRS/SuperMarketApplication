package org.jsp.SuperMarket.controller;

import java.util.List;

import org.jsp.SuperMarket.dto.Cart;
import org.jsp.SuperMarket.dto.Customer;
import org.jsp.SuperMarket.dto.Product;
import org.jsp.SuperMarket.dto.ShoppingOrders;
import org.jsp.SuperMarket.dto.Customer;
import org.jsp.SuperMarket.exception.AllException;
import org.jsp.SuperMarket.helper.Login;
import org.jsp.SuperMarket.helper.ResponseStructure;
import org.jsp.SuperMarket.service.CustomerService;
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
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("signup")
	public ResponseStructure<Customer> signup(@RequestBody Customer customer){
		return customerService.signup(customer);
	}
	
	@PutMapping("verify/{id}/{otp}")
	public ResponseStructure<Customer> verify(@PathVariable String id,@PathVariable int otp) throws AllException{
		return customerService.verify(id,otp);
	}
	
	@PostMapping("login")
	public ResponseStructure<Customer> login(@RequestBody Login login) throws AllException{
		return customerService.login(login);
	}
	
	@GetMapping("product/fetch")
	public ResponseStructure<List<Product>> fetch() throws AllException{
		return customerService.fetchProduct();
	}
	
	@PutMapping("cart/add/{cid}/{p_id}")
	public ResponseStructure<Cart> addToCart(@PathVariable String cid,@PathVariable int p_id){
		return customerService.addToCart(cid,p_id);
	}
	
	@DeleteMapping("cart/remove/{cid}/{p_id}")
	public ResponseStructure<Cart> removeFromCart(@PathVariable String cid,@PathVariable int p_id) throws AllException{
		return customerService.removeFromCart(cid,p_id);
	}
	
	@PutMapping("order/place/{cid}")
	public ResponseStructure<List<ShoppingOrders>> placeOrder(@PathVariable String cid) throws AllException{
		return customerService.placeOrder(cid);
	}
	
	@PutMapping("wallet/{cid}/{amt}")
	public ResponseStructure<Customer> addMoney(@PathVariable String cid,@PathVariable double amt){
		return customerService.addMoney(cid,amt);
	}
	
	@GetMapping("order/fetch/{cid}")
	public ResponseStructure<List<ShoppingOrders>> viewAllOrder(@PathVariable String cid) throws AllException{
		return customerService.viewAllOrder(cid);
	}
	
	@PutMapping("review/{p_id}")
	public ResponseStructure<Product> review(@PathVariable int p_id){
		return customerService.review(p_id);
	}

}
