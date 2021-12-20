package com.items.app.clients;

import java.util.List;

//import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.commons.app.models.entity.Product;

//@FeignClient(name = "products-service", url = "localhost:8081/api/products")
@FeignClient(name = "products-service")
public interface ProductClientRest {

	@GetMapping
	public ResponseEntity<List<Product>> getAll();
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(name="id") Long id);
	
	@PostMapping
	public ResponseEntity<Product> save(@RequestBody Product product);
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long id);
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id);
}
