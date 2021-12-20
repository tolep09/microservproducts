package com.products.app.models.service;

import java.util.List;

//import com.products.app.models.entity.Product;
import com.commons.app.models.entity.Product;

public interface IProductService {
	public abstract List<Product> findAll();
	public abstract Product findById(Long id);
	public abstract Product save(Product product);
	public abstract void deleteById(Long id);
}
