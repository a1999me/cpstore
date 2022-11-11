package com.cy.store.service;

import com.cy.store.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findHostList();
    Product findById(Integer id);
}
