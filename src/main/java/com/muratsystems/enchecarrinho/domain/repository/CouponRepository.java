package com.muratsystems.enchecarrinho.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muratsystems.enchecarrinho.domain.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Optional<Coupon> findByCode(String code);
	
}
