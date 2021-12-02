package com.products.app.models.service;

import java.util.List;

import com.products.app.models.entity.Product;

public interface IProductService {
	public abstract List<Product> findAll();
	public abstract Product findById(Long id);
}