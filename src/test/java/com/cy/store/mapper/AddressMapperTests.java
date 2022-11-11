package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;


    @Test
    public void insert(){
        Address address=new Address();
        address.setUid(1);
        address.setPhone("44562");
        address.setName("女朋友");
        addressMapper.insert(address);
    }
    @Test
    public void countByUid(){
        Integer count=addressMapper.countByUid(1);
        System.out.println(count);
    }
    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(17);
        System.out.println(list);

    }
    @Test
    public void findByAid(){
        List<Address> byUid = addressMapper.findByUid(18);
        System.out.println(byUid);
    }
    @Test
    public void updateNoneDefault(){
        Integer integer = addressMapper.updateNoneDefault(17);
        System.out.println(integer);
    }
    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(7,"小小牛子",new Date());
    }

    @Test
    public  void deleteByAid(){
        addressMapper.deleteByAid(18);
    }
    @Test
    public void findLastModified(){
        System.out.println(addressMapper.findLastModified(18));
    }
}
