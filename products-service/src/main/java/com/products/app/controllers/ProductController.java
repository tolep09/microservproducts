package com.products.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.products.app.models.entity.Product;
import com.products.app.models.service.IProductService;

@RestController
public class ProductController {
	private final Logger logger = LoggerFactory.getLogger(ProductController.class);
	private IProductService productService;
	
	@Autowired
	public ProductController(IProductService productService) {
		logger.info("inyectando productService");
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Product> products = productService.findAll();
		if (products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(products);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> getProduct(@PathVariable(name="id") Long id) {
		Product product = productService.findById(id);
		
		if (product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(product);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Product product) {
		Product tmpProd = productService.save(product);
		
		if (tmpProd != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(tmpProd);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<?> update(@RequestBody Product product, @PathVariable Long id) {
		Product proddb = productService.findById(id);
		
		if (proddb == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			proddb.setName(product.getName());
			proddb.setPrice(product.getPrice());
			
			productService.save(proddb);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(proddb);
		}
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		logger.info("--------------delete de product");
		Product proddb = productService.findById(id);
		
		if (proddb == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			productService.deleteById(id);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}
