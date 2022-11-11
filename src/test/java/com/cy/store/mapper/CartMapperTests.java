package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {
    @Autowired
    private CartMapper mapper;
    @Test
    public void insert(){
        Cart cart=new Cart();
        cart.setUid(18);
        cart.setPid(10000001);
        cart.setPrice(1000L);
        cart.setNum(2);
        mapper.insert(cart);
    }
    @Test
    public void update(){
        mapper.updateNumByCid(1,2,"张三",new Date());
    }
    @Test
    public void select(){
        Cart cart = mapper.findByUidAndPid(18, 10000001);
        System.out.println(cart);
    }
    @Test
    public void  findVOByUid(){
        System.out.println(mapper.findVOByUid(18));
    }
    @Test
    public void findOVByCid(){
        Integer[] cids={1,2,3,4,50,60};
        System.out.println(mapper.findVOByCid(cids));
    }
}
