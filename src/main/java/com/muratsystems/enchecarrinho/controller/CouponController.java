package com.muratsystems.enchecarrinho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muratsystems.enchecarrinho.model.dto.CouponDTO;
import com.muratsystems.enchecarrinho.model.service.CouponService;

@RestController
@RequestMapping(value = "/coupons")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@GetMapping
	public List<CouponDTO> getAllCoupons() {
		return null;
	}
	
	@PostMapping(value = "/new")
	public void saveCoupon() {
		
	}
	
	@PutMapping(value = "/{id}/update")
	public void updateCoupon() {
		
	}
	
	@DeleteMapping(value = "/{id}/delete")
	public void deleteCoupon() {
		
	}
	
}
