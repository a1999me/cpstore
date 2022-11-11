package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.apache.ibatis.annotations.Param;

import java.net.Inet4Address;
import java.util.Date;
import java.util.List;

/**
 * 收货地址持久层的就扣
 */
public interface AddressMapper {
    //插入用户的收货地址数据
    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址的数量
     *
     * @param uid
     * @return 当前用户的收获总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的id查询用户的收货地址数据
     *
     * @param uid
     * @return
     */
    List<Address> findByUid(Integer uid);

    /**
     * 查找默认地址是否存在
     */
    Address findByAid(Integer aid);

    Integer updateNoneDefault(Integer uid);

    Integer updateDefaultByAid(@Param("aid") Integer aid,
                               @Param("modifiedUser") String modifiedUser,
                               @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据收货地址的id删除
     * @param aid
     * @return
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户uid查询当前用户最后一次修改的地址收货数据
     * @param uid
     * @return
     */
     Address findLastModified(Integer uid);

}


