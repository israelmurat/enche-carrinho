package com.muratsystems.enchecarrinho.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muratsystems.enchecarrinho.model.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
