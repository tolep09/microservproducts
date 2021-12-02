package com.items.app.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.items.app.models.Product;

//@FeignClient(name = "products-service", url = "localhost:8081/api/products")
@FeignClient(name = "products-service")
public interface ProductClientRest {
	@GetMapping
	public List<Product> findAll();
	
	@GetMapping(value = "/{id}")
	public Product findById(@PathVariable (name = "id") Long id);
}
