package com.cy.store.service;

import com.cy.store.vo.CartVo;

import java.util.List;

public interface ICartService {
    /**
     * 将商品添加到购物车中
     * @param uid 用户id
     * @param pid 商品 id
     * @param amount 新增的数量
     * @param username 用户名
     */
    void addTOCart(Integer uid,Integer pid,Integer amount,String username);
    List<CartVo>getVOByUid(Integer uid);

    /**
     * 更新用户购物车的数量
     * @param cid
     * @param uid
     * @param username
     * @return
     */
    Integer addNum(Integer cid,Integer uid,String username);
    List<CartVo>getVOByCid(Integer uid,Integer[]cids);

}
