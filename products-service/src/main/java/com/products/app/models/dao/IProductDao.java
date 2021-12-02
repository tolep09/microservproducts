package com.products.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.products.app.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

}
