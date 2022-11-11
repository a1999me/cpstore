package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest //表示标注下的类是一个测试类
//自己定义的测试类要把这个注解写上 Runwith：启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class DistrictMapperTests {
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent(){
        List<District> list = districtMapper.findByParent("210100");
        for (District dd:list
             ) {
            System.out.println(dd);
        }

        }
        @Test
        public void findNameByCode(){
            String name = districtMapper.findNameByCode("610000");
            System.out.println(name);
        }
}
