package com.items.app.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.items.app.models.Item;
import com.commons.app.models.entity.Product;

@Service("serviceRTemplate")
//@Primary
public class ItemServiceImpl implements IItemService {
	private final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
	@Autowired
	private RestTemplate clientRest;

	@Override
	public List<Item> findAll() {
		logger.info("servicio: http://products-service");
		List<Product> products = Arrays.asList(
				clientRest.getForObject("http://products-service", Product[].class)
				);
		return products.stream().map(product -> new Item(product, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		Map<String, String> arguments = new HashMap<>();
		arguments.put("id", id.toString());
		Product product = clientRest.getForObject(
				"http://products-service/{id}",
				Product.class,
				arguments
		);
				
		return new Item(product, quantity);
	}

	@Override
	public Product save(Product product) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> rEntity = clientRest.exchange("http://products-service", HttpMethod.POST, body, Product.class);
		return rEntity.getBody();
	}

	@Override
	public Product update(Product product, Long id) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		Map<String, String> arguments = new HashMap<>();
		arguments.put("id", id.toString());
		ResponseEntity<Product> rEntity = clientRest.exchange("http://products-service/{id}", HttpMethod.PUT, body, Product.class, arguments);
		return rEntity.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> arguments = new HashMap<>();
		arguments.put("id", id.toString());
		
		clientRest.delete("http://products-service/{id}", arguments);
	}

}
