package com.items.app.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.items.app.clients.ProductClientRest;
import com.items.app.models.Item;
import com.items.app.models.Product;

@Service("serviceFeign")
@Primary // indicar que es el que se debe inyectar en el controller
public class ItemServiceFeign implements IItemService {
	
	@Autowired
	private ProductClientRest productClientRest;

	@Override
	public List<Item> findAll() {
		return productClientRest.getAll().getBody().stream().map(product -> new Item(product, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		return new Item(productClientRest.getProduct(id).getBody(), quantity);
	}
	
	
	@Override
	public Product save(Product product) {
		return productClientRest.save(product).getBody();
	}

	@Override
	public Product update(Product product, Long id) {
		return productClientRest.update(product, id).getBody();
	}

	@Override
	public void delete(Long id) {
		productClientRest.delete(id);
	}

}
