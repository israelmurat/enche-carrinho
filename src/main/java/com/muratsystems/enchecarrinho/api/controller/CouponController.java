package com.muratsystems.enchecarrinho.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.muratsystems.enchecarrinho.api.dto.CouponDTO;
import com.muratsystems.enchecarrinho.domain.service.CouponService;

@RestController
@RequestMapping(value = "/coupons")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@GetMapping
	public ResponseEntity<List<CouponDTO>> getAllCoupons() {
		return new ResponseEntity<>(couponService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{idCoupon}")
	public ResponseEntity<CouponDTO> getById(@PathVariable Long idCoupon) {
		return new ResponseEntity<>(couponService.findById(idCoupon), HttpStatus.OK);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) 
	public CouponDTO addCoupon(@RequestBody @Valid CouponDTO couponDTO) {
		return couponService.addCoupon(couponDTO);
	}
	
	@PutMapping(value = "/{idCoupon}")
	public ResponseEntity<CouponDTO> updateCoupon(@Valid @PathVariable Long idCoupon, 
			@RequestBody CouponDTO CouponDTO) {
		return new ResponseEntity<>(couponService.update(idCoupon, CouponDTO), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{idCoupon}")
	public ResponseEntity<Void> deleteCoupon(@PathVariable Long idCoupon) {
		couponService.delete(idCoupon);
		return ResponseEntity.noContent().build();
	}
	
}
