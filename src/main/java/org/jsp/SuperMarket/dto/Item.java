package org.jsp.SuperMarket.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Component
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int i_id;
	String i_name;
	double i_price;
	int i_quantity;
}
