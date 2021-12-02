package com.items.app.models.service;

import java.util.List;

import com.items.app.models.Item;

public interface IItemService {
	public abstract List<Item> findAll();
	public abstract Item findById(Long id, Integer quantity);
}
