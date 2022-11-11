package com.cy.store.service;

import com.cy.store.entity.District;
import com.cy.store.service.impl.IDistructServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {
    @Autowired
    private IDistructService service;
    @Test
    public void test1(){
        //86代表中国，所有的省的父区代号
        List<District> d = service.getByParent("84");
        for (District k:d
             ) {
            System.err.println(k);
        }
    }

}
