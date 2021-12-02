package com.products.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.products.app.models.dao.IProductDao;
import com.products.app.models.entity.Product;

@Service
public class ProductServiceImpl implements IProductService {
	private IProductDao productDao;
	
	@Autowired
	public ProductServiceImpl(IProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return (List<Product>)productDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return productDao.findById(id).orElse(null);
	}

}
