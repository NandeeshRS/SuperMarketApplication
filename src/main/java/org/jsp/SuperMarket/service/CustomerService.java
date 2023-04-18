package org.jsp.SuperMarket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsp.SuperMarket.dao.Customerdao;
import org.jsp.SuperMarket.dao.Merchantdao;
import org.jsp.SuperMarket.dao.Productdao;
import org.jsp.SuperMarket.dto.Cart;
import org.jsp.SuperMarket.dto.Customer;
import org.jsp.SuperMarket.dto.Item;
import org.jsp.SuperMarket.dto.Merchant;
import org.jsp.SuperMarket.dto.Product;
import org.jsp.SuperMarket.dto.ShoppingOrders;
import org.jsp.SuperMarket.dto.Customer;
import org.jsp.SuperMarket.exception.AllException;
import org.jsp.SuperMarket.helper.Login;
import org.jsp.SuperMarket.helper.ResponseStructure;
import org.jsp.SuperMarket.helper.VerificationEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	VerificationEmailSender emailSender;
	
	@Autowired
	Customerdao customerdao;

	@Autowired
	Productdao productdao;
	
	@Autowired
	Cart cart;
	
	@Autowired
	Item item;
	
	@Autowired
	ShoppingOrders orders;
	
	@Autowired
	Merchantdao merchantdao;
	
	public ResponseStructure<Customer> signup(Customer customer) {
ResponseStructure<Customer> structure=new ResponseStructure<>();
		
		customer.setOtp(new Random().nextInt(100000,999999));
		emailSender.sendEmail(customer);
		
		structure.setMessage("Verification Mail Sent Verify OTP to Create Account");
		structure.setStatuscode(HttpStatus.PROCESSING.value());
		structure.setData(customerdao.save(customer));
		
		return structure;
		
	}

	public ResponseStructure<Customer> verify(String id, int otp) throws AllException {
		ResponseStructure<Customer> structure=new ResponseStructure<>();
		Customer Customer=customerdao.find(id);
		if(Customer.getOtp()==otp) {
			Customer.setStatus(true);
			structure.setMessage("Account Created Successfully");
			structure.setStatuscode(HttpStatus.CREATED.value());
			structure.setData(customerdao.save(Customer));
		}else {
			throw new AllException("otp mismatch");
		}
		
		return structure;
	}

	public ResponseStructure<Customer> login(Login login) throws AllException {
		ResponseStructure<Customer> structure=new ResponseStructure<>();
		Customer customer=customerdao.find(login.getId());
		if(customer==null) {
			throw new AllException("Invalid Id");
		}else {
			if(customer.getPassword().equals(login.getPassword())) {
				if(customer.isStatus()) {
				structure.setData(customer);
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

	public ResponseStructure<List<Product>> fetchProduct() throws AllException {
		ResponseStructure<List<Product>> structure=new ResponseStructure<>();
		
		List<Product> products= productdao.fetchCustomerProduct();
		if(products.isEmpty()) {
			throw new AllException("Products Not Found");
		}else {
		
		structure.setData(products);
		structure.setMessage("Found Successfull");
		structure.setStatuscode(HttpStatus.FOUND.value());
		}
		return structure;
	}

	public ResponseStructure<Cart> addToCart(String cid, int p_id) {
		ResponseStructure<Cart> structure=new ResponseStructure<>();
		Customer customer=customerdao.find(cid);
		Product product=productdao.find(p_id);
		
		Cart cart= customer.getCart();
		if(cart==null) {
			cart=this.cart;
		}
		
		List<Item> items=cart.getItems();
		if(items==null) {
			items=new ArrayList<Item>();
		}
		if(items.isEmpty()) {
			item.setI_name(product.getPname());
			item.setI_price(product.getPrice());
			item.setI_quantity(1);
			items.add(item);
		}else {
			boolean flag=false;
			for(Item item:items) {
				if(item.getI_name().equals(product.getPname())) {
					item.setI_quantity(item.getI_quantity()+1);
					item.setI_price(item.getI_price()+product.getPrice());
					flag=false;
					break;
				}else {
					flag=true;
				}
			}
				if(flag) {
					item.setI_name(product.getPname());
					item.setI_price(product.getPrice());
					item.setI_quantity(1);
					items.add(item);
				}
			}
		cart.setItems(items);
		customer.setCart(cart);
		
		structure.setMessage("Added To Cart");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		structure.setData(customerdao.save(customer).getCart());
		return structure;
	}

	public ResponseStructure<Cart> removeFromCart(String cid, int p_id) throws AllException {
		ResponseStructure<Cart> structure=new ResponseStructure<>();
		Customer customer=customerdao.find(cid);
		Product product=productdao.find(p_id);
		
		Cart cart= customer.getCart();
		List<Item> items=cart.getItems();
		if(items.isEmpty()) {
			throw new AllException("No Items In The Cart");
		}else {
			Item item2=null;
			for(Item item:items) {
				if(item.getI_name().equals(product.getPname())) {
					if(item.getI_quantity()>1) {
						item.setI_quantity(item.getI_quantity()-1);
						item.setI_price(item.getI_price()-product.getPrice());
					}else {
						item2=item;
					}
				}
			}
			if(item2!=null) {
				items.remove(item2);
			}
		}
		
		cart.setItems(items);
		customer.setCart(cart);
		
		structure.setMessage("Removed From Cart");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		structure.setData(customerdao.save(customer).getCart());
		return structure;
	}

	public ResponseStructure<List<ShoppingOrders>> placeOrder(String cid) throws AllException {
		ResponseStructure<List<ShoppingOrders>> structure=new ResponseStructure<>();
		
		Customer customer=customerdao.find(cid);
		Cart cart= customer.getCart();
		List<Item> items=cart.getItems();
		double price=0;
		for(Item item:items) {
			price=price+item.getI_price();
		}
		List<Item> items2=new ArrayList<>();
		items2.addAll(items);
		
		orders.setDateTime(LocalDateTime.now());
		orders.setItems(items2);
		orders.setPrice(price);
		
		List<ShoppingOrders> list=customer.getOrders();
		if(list==null) {
			list=new ArrayList<>();
		}
		list.add(orders);
		
		customer.setOrders(list);
		
		if(customer.getWallet()<price) {
			throw new AllException("Insufficient Fund To Place Order");
		}else {
			for (Item item : orders.getItems()) {
				Product product = productdao.find(item.getI_name());
				
				if(product.getStock()<item.getI_quantity()) {
					throw new AllException("Out of Stock");
				}else {
					product.setStock(product.getStock()-item.getI_quantity());
				}
				Merchant merchant = product.getMerchant();
				merchant.setWallet(merchant.getWallet() + item.getI_price());

				merchantdao.save(merchant);
			}
			customer.setWallet(customer.getWallet()-price);
			customer.setCart(null);
			structure.setMessage("Order Placed");
			structure.setStatuscode(HttpStatus.ACCEPTED.value());
			structure.setData(customerdao.save(customer).getOrders());
		}
		
		return structure;
	}

	public ResponseStructure<Customer> addMoney(String cid, double amt) {
		ResponseStructure<Customer> structure=new ResponseStructure<>();
		Customer customer=customerdao.find(cid);
		customer.setWallet(customer.getWallet()+amt);
		
		structure.setMessage("Added Money");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		structure.setData(customerdao.save(customer));
		
		return structure;
	}

	public ResponseStructure<List<ShoppingOrders>> viewAllOrder(String cid) throws AllException {
		ResponseStructure<List<ShoppingOrders>> structure=new ResponseStructure<>();
		Customer customer=customerdao.find(cid);
		List<ShoppingOrders>orders=customer.getOrders();
		if(orders.isEmpty()) {
			throw new AllException("No Orders Found");
		}else {
			structure.setMessage("Orders Found");
			structure.setStatuscode(HttpStatus.FOUND.value());
			structure.setData(orders);
		}
	
		return structure;
	}

	public ResponseStructure<Product> review(int p_id) {
		ResponseStructure<Product> structure=new ResponseStructure<>();
		
		Product product=productdao.find(p_id);
		product.setBadreview(product.getBadreview()+1);
		
		if(product.getBadreview()>3) {
			product.setStatus(false);
		}
		structure.setMessage("Review Updated");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		structure.setData(productdao.save(product));
		
		return structure;
	}

}
