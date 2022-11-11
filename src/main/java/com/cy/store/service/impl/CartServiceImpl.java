package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    /**
     * 购物车的业务层依赖于购物车和商品的持久层
     */
    @Autowired
    private CartMapper cartmapper;
    @Autowired
    private ProductMapper productmapper;
    @Override
    public void addTOCart(Integer uid, Integer pid, Integer amount, String username) {
        //先去查询当前添加的这个购物车是否在表中已存在
        Cart res = cartmapper.findByUidAndPid(uid, pid);
        if (res==null){
            Cart cart=new Cart();
            //补全数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            //表明从来没有加入到购物车内

            //价格：来自于商品中的数据
            Product product = productmapper.findById(pid);
            cart.setPrice(product.getPrice());
            //补全四个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedTime(new Date());
            cart.setModifiedUser(username);
            Integer rows = cartmapper.insert(cart);
            if (rows!=1){
                throw new InsertException("插入数据时产生未知的异常");
            }
        }else{
            Integer i = res.getNum() + amount;
            Integer rows = cartmapper.updateNumByCid(res.getCid(), i, username, new Date());
            if (rows!=1){
                throw new UpdateException("更新时产生未知的异常！");
            }

        }
    }

    @Override
    public List<CartVo> getVOByUid(Integer uid) {
        return cartmapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartmapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartmapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
        return num;

    }

    @Override
    public List<CartVo> getVOByCid(Integer uid, Integer[] cids) {
        List<CartVo>list=cartmapper.findVOByCid(cids);
        Iterator<CartVo> iterator = list.iterator();
        //判断是否有下一个元素
        while (iterator.hasNext()){
            //拿到该元素
            CartVo cartVo = iterator.next();
            if (!cartVo.getUid().equals(uid)){ //表示当前的数据不属于当前的用户
                //从集合当中移除该元素
                list.remove(cartVo);
            }
        }
        return list;
    }
}
