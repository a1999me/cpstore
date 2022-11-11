package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.function.ToIntBiFunction;

@SpringBootTest //表示标注下的类是一个测试类
//自己定义的测试类要把这个注解写上 Runwith：启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {
    //userMapper爆红的原因： idea具有检测的功能，接口是不能直接创建bean的，（用动态代理来解决）
    @Autowired
    private UserMapper userMapper;
    /**
     * 必须被@Test所修饰，
     * 返回值类型必须是void
     * 方法的参数列表不能指定任何类型
     * 方法的返回值必须是public
     */
    @Test
    public void inset(){
        User user=new User();
        user.setUsername("tim");
        user.setPassword("123456");
        Integer rows=userMapper.insert(user);
        //如果有整数1的输出那么就是成功的
        System.out.println(rows);

    }
    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(4,"852","管理员",new Date());
    }
    @Test
    public void findById(){
        System.out.println(userMapper.findByUid(4));
    }
    @Test
    public void updateInfoByUid(){
       User user=new User();
       user.setUid(9);
       user.setPhone("112023456");
       user.setEmail("oo2@163.com");
       user.setGender(1);
       userMapper.updateInfoByUid(user);
    }
    @Test
    public void  updateAvaterByUid(){
        userMapper.updateAvatarByUid(12,
                "/upload/avatar.png",
                "好人",
                new Date());

    }
}
