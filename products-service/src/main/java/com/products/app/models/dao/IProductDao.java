package com.products.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.commons.app.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

}
