package com.gm2.pvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gm2.pvd.entity.ItemSale;

@Repository
public interface ItemSaleRepository extends JpaRepository<ItemSale, Long>{

}


