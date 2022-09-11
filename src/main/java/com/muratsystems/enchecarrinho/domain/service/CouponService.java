package com.muratsystems.enchecarrinho.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muratsystems.enchecarrinho.api.dto.CouponDTO;
import com.muratsystems.enchecarrinho.domain.exception.BusinessException;
import com.muratsystems.enchecarrinho.domain.model.Coupon;
import com.muratsystems.enchecarrinho.domain.repository.CouponRepository;

@Service
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;
	
	public CouponDTO addProduct(CouponDTO couponDTO) {
		Optional<Coupon> optCoupon =  couponRepository.findByCode(couponDTO.getCode());
		if (optCoupon.isPresent()) {
			throw new BusinessException("Já existe um cupom cadastrado com este código!");
		}
		return new CouponDTO(couponRepository.save(new Coupon(couponDTO)));
	}
	
}
