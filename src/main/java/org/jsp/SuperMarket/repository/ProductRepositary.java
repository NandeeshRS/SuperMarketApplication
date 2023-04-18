package org.jsp.SuperMarket.repository;

import java.util.List;

import org.jsp.SuperMarket.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositary extends JpaRepository<Product, Integer>{

	List<Product> findByStatus(boolean b);

	Product findByPname(String name);

	
}
