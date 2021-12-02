package com.items.app.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.items.app.models.Item;
import com.items.app.models.Product;

@Service
public class ItemServiceImpl implements IItemService {
	@Autowired
	private RestTemplate clientRest;

	@Override
	public List<Item> findAll() {
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

}
