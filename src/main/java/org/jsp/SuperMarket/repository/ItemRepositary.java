package org.jsp.SuperMarket.repository;

import org.jsp.SuperMarket.dto.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositary extends JpaRepository<Item, Integer> {

}
