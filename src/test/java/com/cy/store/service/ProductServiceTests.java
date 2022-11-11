package com.cy.store.service;

import com.cy.store.entity.Product;
import com.cy.store.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {
    @Autowired
    private IProductService productService;
    @Test
    public void  test1(){
        Product byId = productService.findById(10000002);
        System.out.println(byId);

    }
}
