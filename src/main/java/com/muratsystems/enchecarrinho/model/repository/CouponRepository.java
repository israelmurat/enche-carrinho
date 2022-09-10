package com.muratsystems.enchecarrinho.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muratsystems.enchecarrinho.model.domain.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Optional<Coupon> findByCode(String code);
	
}
