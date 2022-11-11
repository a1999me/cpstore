package com.cy.store.service;

import com.cy.store.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderService {
    @Autowired IOrderService orderService;
    @Test
    public void creat(){
        Integer[]cids={1};
        Order order = orderService.create(18, 18, "小可爱", cids);
        System.out.println(order);
    }
}
