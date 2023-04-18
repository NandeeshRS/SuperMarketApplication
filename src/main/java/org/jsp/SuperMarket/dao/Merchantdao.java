package org.jsp.SuperMarket.dao;

import java.util.Optional;

import org.jsp.SuperMarket.dto.Merchant;
import org.jsp.SuperMarket.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Merchantdao {

	@Autowired
	MerchantRepository merchantRepository;
	
	public Merchant save(Merchant merchant) {
		return merchantRepository.save(merchant);
	}

	public Merchant find(String id) {
		Optional<Merchant> optional=merchantRepository.findById(id);
		if(optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
		
	}

}
