package org.jsp.SuperMarket.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@Component
public class ShoppingOrders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int o_id;
	double price;
	LocalDateTime dateTime;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	List<Item> items;

	
}
