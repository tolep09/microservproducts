package com.items.app.models.service;

import java.util.List;

import com.items.app.models.Item;
import com.commons.app.models.entity.Product;

public interface IItemService {
	public abstract List<Item> findAll();
	public abstract Item findById(Long id, Integer quantity);
	public abstract Product save(Product product);
	public abstract Product update(Product product, Long id);
	public abstract void delete(Long id);
}
