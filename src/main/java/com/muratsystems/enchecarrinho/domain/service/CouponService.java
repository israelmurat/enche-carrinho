package com.muratsystems.enchecarrinho.domain.service;

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
		if (couponRepository.findByCode(couponDTO.getCode()).isPresent()) {
			throw new BusinessException("Já existe um cupom cadastrado com este código!");
		}
		return toDto(couponRepository.save(toEntity(couponDTO)));
	}
	
	public CouponDTO findById(Long idCoupon) {
		return toDto(couponRepository.findById(idCoupon)
				.orElseThrow(() -> new BusinessException("Cupom não encontrado!")));
	}
	
	public CouponDTO findByCode(String code) {
		return toDto(couponRepository.findByCode(code)
				.orElseThrow(() -> new BusinessException("Código do cupon inexistente!")));
	}
	
	private Coupon toEntity(CouponDTO couponDTO) {
		return modelMapper.map(couponDTO, Coupon.class);
	}
	
	private CouponDTO toDto(Coupon coupon) {
		return modelMapper.map(coupon, CouponDTO.class);
	}
	
}
