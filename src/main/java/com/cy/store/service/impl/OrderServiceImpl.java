package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ICartService;
import com.cy.store.service.IOrderService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private ICartService cartService;

    @Override
    public Order create(Integer aid, Integer uid, String username, Integer[] cids) {
        //即将下单的列表
        List<CartVo> list = cartService.getVOByCid(uid, cids);
        //计算商品的总价
        long totalPrice = 0L;
        for (CartVo c : list) {
            totalPrice += c.getRealPrice() * c.getNum();
        }
        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);

        //封装收货地址
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());

        //封装创建时间,支付状态和总价
        order.setOrderTime(new Date());
        order.setStatus(0);
        order.setTotalPrice(totalPrice);

        //封装四个日志
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());
        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("插入数据时产生未知的异常");
        }
        //创建订单项的数据
        for (CartVo c:list
             ) {
            //创建一个订单项
            OrderItem orderItem=new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(c.getPid());
            orderItem.setTitle(c.getTitle());
            orderItem.setImage(c.getImage());
            orderItem.setPrice(c.getRealPrice());
            orderItem.setNum(c.getNum());
            //日志字段
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());
            //插入数据操作
             rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1) {
                throw new InsertException("插入数据时产生未知的异常");
            }
        }
        return order;
    }

}
