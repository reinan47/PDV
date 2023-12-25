package com.gm2.pvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gm2.pvd.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
