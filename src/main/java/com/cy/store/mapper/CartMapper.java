package com.cy.store.mapper;

import com.cy.store.entity.BaseEntity;
import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    /**
     * 插入数据
     * @param cart
     * @return
     */
    Integer insert(Cart cart);

    /**
     * 更新数据
     * @param cid
     * @param num
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateNumByCid(@Param("cid") Integer cid, @Param("num") Integer num, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户的的id和商品id来查询购物城中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return
     */
    Cart findByUidAndPid(@Param("uid") Integer uid,@Param("pid") Integer pid);


    List<CartVo> findVOByUid(Integer uid);

    Cart findByCid(Integer cid);
    List<CartVo>findVOByCid(Integer[] cids);

}
