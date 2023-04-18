package org.jsp.SuperMarket.repository;

import org.jsp.SuperMarket.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositary extends JpaRepository<Customer, String>{

}
