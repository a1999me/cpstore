package com.cy.store.service;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressTests {
    @Autowired
    private IAddressService addressService;
    @Test
   public void addNewAddress(){
        Address address=new Address();
        address.setPhone("4456223");
        address.setName("难朋友");
        addressService.addNewAddress(1,"小牛",address);
    }
    @Test
    public void updateDefault(){
        addressService.setDefault(11,18,"我是一头小牛");
    }
    @Test
    public void delete(){
        addressService.delete(15,18,"Emmmmm");
    }
}
