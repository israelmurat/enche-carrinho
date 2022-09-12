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
	
	public CouponDTO addCoupon(CouponDTO couponDTO) {
		Optional<Coupon> optCoupon =  couponRepository.findByCode(couponDTO.getCode());
		if (optCoupon.isPresent()) {
			throw new BusinessException("Já existe um cupom cadastrado com este código!");
		}
		return new CouponDTO(couponRepository.save(new Coupon(couponDTO)));
	}
	
	public Optional<CouponDTO> findById(Long idCoupon) {
		return Optional.ofNullable(new CouponDTO(couponRepository.findById(idCoupon).get()));
	}
	
	public Optional<CouponDTO> findByCode(String code) {
		Optional<Coupon> optCoupon = couponRepository.findByCode(code);
		if (optCoupon.isPresent()) {
			return Optional.ofNullable(new CouponDTO(couponRepository.findByCode(code).get()));
		}
		return Optional.empty();
	}
	
}
