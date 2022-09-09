package com.muratsystems.enchecarrinho.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muratsystems.enchecarrinho.model.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
