package com.muratsystems.enchecarrinho.domain.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CouponDTO addCoupon(CouponDTO couponDTO) {
		Optional<Coupon> optCoupon =  couponRepository.findByCode(couponDTO.getCode());
		if (optCoupon.isPresent()) {
			throw new BusinessException("Já existe um cupom cadastrado com este código!");
		}
		return toDto(couponRepository.save(toEntity(couponDTO)));
	}
	
	public Optional<CouponDTO> findById(Long idCoupon) {
		return Optional.ofNullable(toDto(couponRepository.findById(idCoupon).get()));
	}
	
	public Optional<CouponDTO> findByCode(String code) {
		Optional<Coupon> optCoupon = couponRepository.findByCode(code);
		if (optCoupon.isPresent()) {
			return Optional.ofNullable(toDto(optCoupon.get()));
		}
		return Optional.empty();
	}
	
	private Coupon toEntity(CouponDTO couponDTO) {
		return modelMapper.map(couponDTO, Coupon.class);
	}
	
	private CouponDTO toDto(Coupon coupon) {
		return modelMapper.map(coupon, CouponDTO.class);
	}
	
}
