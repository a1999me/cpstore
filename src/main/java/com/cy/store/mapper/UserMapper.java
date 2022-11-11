package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块的持久层
 */

public interface UserMapper {
    //用户的注册,插入用户的数据
    Integer insert(User user);
    //根据用户名来查询用户的数据，如果找到对应的用户则返回这个用户的数据，如果没有找到则返回null值
    User findByUsername(String username);

    void findByUsername(User user);

    /**
     * 根据用户的uid来修改用户的密码
     * @param uid
     * @return
     */
    Integer updatePasswordByUid(@Param("uid") Integer uid, @Param("password") String password,
                                @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);
    //查找uid
    User findByUid(Integer uid);

    /**
     * 更新用户的数据信息
     * @param user 用户的数据
     * @return 返回值为受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     *
     * 修改用户的头像，根据用户的uid值来修改用户的头像
     * @Param("SQL映射文件中#{}占位符的变量名")
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
