package com.products.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.products.app.models.entity.Product;
import com.products.app.models.service.IProductService;

@RestController
public class ProductController {
	private IProductService productService;
	
	@Autowired
	public ProductController(IProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Product> products = productService.findAll();
		if (products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok(products);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> getProduct(@PathVariable(name="id") Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}
}
