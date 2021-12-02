package com.items.app.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.items.app.clients.ProductClientRest;
import com.items.app.models.Item;

@Service("serviceFeign")
@Primary // indicar que es el que se debe inyectar en el controller
public class ItemServiceFeign implements IItemService {
	// usando feign
	@Autowired
	private ProductClientRest productClientRest;

	@Override
	public List<Item> findAll() {
		return productClientRest.findAll().stream().map(product -> new Item(product, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		return new Item(productClientRest.findById(id), quantity);
	}

}
