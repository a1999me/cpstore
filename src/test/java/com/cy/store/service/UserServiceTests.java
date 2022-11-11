package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest //表示标注下的类是一个测试类
//自己定义的测试类要把这个注解写上 Runwith：启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserServiceTests {
    //userMapper爆红的原因： idea具有检测的功能，接口是不能直接创建bean的，（用动态代理来解决）
    @Autowired
    private UserServiceImpl userService;
    /**
     * 必须被@Test所修饰，
     * 返回值类型必须是void
     * 方法的参数列表不能指定任何类型
     * 方法的返回值必须是public
     */
    @Test
   public void reg(){
        try {
            User user=new User();
            user.setUsername("bog");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
            System.out.println(user);
        } catch (ServiceException e) {
            //先获取类的的对象，在获取异常类的名称
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void login(){
        User user = userService.login("aka", "456123");
        System.out.println(user);
    }
    @Test
    public void changePassword(){
        userService.changePassword(8,"管理员","123456","741852963");
    }
@Test
    public void test2(){
    System.out.println(userService.getByUid(9));
}
@Test
    public void changInfo(){
        User user=new User();
        user.setPhone("1230");
        user.setEmail("HUAHUA@163.com");
        user.setGender(0);
        userService.changeInfo(12,"zxr",user);
}
@Test
    public void changeAvatar(){
        userService.changeAvatar(12,"/upload/test.png","小明");
}
    }


