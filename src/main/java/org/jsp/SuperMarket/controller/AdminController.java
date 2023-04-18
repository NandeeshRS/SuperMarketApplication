package org.jsp.SuperMarket.controller;

import java.util.List;

import org.jsp.SuperMarket.dto.Product;
import org.jsp.SuperMarket.exception.AllException;
import org.jsp.SuperMarket.helper.Login;
import org.jsp.SuperMarket.helper.ResponseStructure;
import org.jsp.SuperMarket.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
	@Autowired
	AdminService adminService;
	
	@PostMapping("login")
	public ResponseStructure<Login> login(@RequestBody Login login) throws AllException{
		return adminService.login(login);
	}
	
	@GetMapping("product/fetch")
	public ResponseStructure<List<Product>> fetchproducts() throws AllException{
		return adminService.fetchproducts();
	}
	
	@PutMapping("product/status/{p_id}")
	public ResponseStructure<Product> changeStatus(@PathVariable int p_id){
		return adminService.changeStatus(p_id);
	}

}
