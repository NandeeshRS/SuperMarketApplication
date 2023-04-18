package org.jsp.SuperMarket.repository;

import org.jsp.SuperMarket.dto.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {

}
