package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service //将当前对象交给Spring来管理，自动创建对象以及对象的维护
//实现UserService接口
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        //通过User参数来获取传递过来的username
        String username=user.getUsername();
        System.out.println("传入参数，username:"+username);
        //调用Mapper的findByUsername查看用户是否被注册过
        User result=userMapper.findByUsername(username);
        //判断结果集是否为null
        if (result!=null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用！");
        }


        //密码加密处理的实现： md5算法的形式: 89sdsfds-sdfsf12-yrgfdgf4523
        //处理方式： 串 +password+串 然后交给md5加密，连续加载三次
        //盐值+password+盐值  --盐值就是一个随机的字符串
        String oldPassword = user.getPassword();
        //随机生成一个盐值
        String salt = UUID.randomUUID().toString().toUpperCase();

        //将密码和盐值作为一个整体进行加密处理
        System.out.println("盐值是:"+salt);
        String md5Password=getMD5Password(oldPassword,salt);

        //将加密之后的密码重新设置补全到user当中
        user.setSalt(salt);
        user.setPassword(md5Password);



        //补全数据 is_delete设置为0
        user.setIsDelete(0);
        //补全数据：四个日志信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date=new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

            //执行注册功能的实现,如果插入成功row=1
            Integer rows = userMapper.insert(user);

        if (rows!=1){
            throw  new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {

        //根据用户名称查询用户的数据是否存在，如果不存在抛出异常
        User result = userMapper.findByUsername(username);
        if (result==null){
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测用户的密码是否匹配
        //1.先获取到数据库加密的密码
        String oldPassword=result.getPassword();
        //2.和用户传递过来的密码比较
        //2.1先获取盐值：上一次注册时获得的盐值
        String salt = result.getSalt();
        System.out.println("盐值是："+salt);
        //2.2将用户的密码按照相同的md5算法的规则进行加密
        String newMd5Password = getMD5Password(password, salt);
        System.out.println(newMd5Password);
        //3.将密码进行比较
        if(!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断is_delete的数值是否为1，为1表示标记为删除
        if(result.getIsDelete()==1){

            throw new UserNotFoundException("用户数据不存在");
        }
        //调用mapper层的findByIdUsername来查询用户的数据，提升系统的性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        //获取到user头像
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码和数据库中密码进行比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows =userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if (rows!=1){
            throw  new UpdateException("更新数据产生未知异常");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        //根据uid检测用户的数据 首先检测用户存不存在
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        User user=new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setUsername(username);
        user.setModifiedTime(new Date());
        user.setModifiedUser(username);
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows!=1){
            throw new UpdateException("更新数据时产生未知的异常");
        }

    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //1.做头像修改，保证用户的信息是存在的，所以先查询用户的信息是否存在
        User result= userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新用户头像产生的异常");
        }
    }

    /**
     * 定义一个md5算法的加密处理
     */
    private String getMD5Password(String password,String salt){
        //md5加密方法的调用(进行三次加密)
        for (int i=0;i<3;i++){
        password=DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        //返回加密之后的密码
        return password;
    }

}
