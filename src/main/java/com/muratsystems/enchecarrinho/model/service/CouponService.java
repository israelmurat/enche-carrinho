package com.muratsystems.enchecarrinho.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.muratsystems.enchecarrinho.model.BusinessException;
import com.muratsystems.enchecarrinho.model.domain.Coupon;
import com.muratsystems.enchecarrinho.model.domain.Product;
import com.muratsystems.enchecarrinho.model.dto.CouponDTO;
import com.muratsystems.enchecarrinho.model.dto.ProductDTO;
import com.muratsystems.enchecarrinho.model.repository.CouponRepository;

@Service
public class CouponService {

	private CouponRepository couponRepository;
	
	public CouponDTO addProduct(CouponDTO couponDTO) {
		Optional<Coupon> optCoupon =  couponRepository.findByCode(couponDTO.);
		if (optProduct.isPresent()) {
			throw new BusinessException("Já existe um produto cadastrado com esta descrição!");
		}
		return new ProductDTO(productRepository.save(new Product(productDTO)));
	}
	
}
