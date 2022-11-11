package com.cy.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import javax.xml.crypto.Data;

@Configuration //表示配置类
@SpringBootApplication
@MapperScan("com.cy.store.mapper") //在此处添加注解，用于扫描mapper的包，在项目启动时会自动加载所有
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }
     public MultipartConfigElement getMultipartConfigElement(){
        //创建一个配置工厂类的对象
         MultipartConfigFactory factory=new MultipartConfigFactory();
         //设置需要船舰的对象的相关信息
         factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
         factory.setMaxRequestSize(DataSize.of(15,DataUnit.MEGABYTES));
         //通过工厂类来创建MultipartConfigElement对象
         return factory.createMultipartConfig();

     }


}
