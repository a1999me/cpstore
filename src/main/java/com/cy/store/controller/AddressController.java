package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("addresses")
@RestController
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void>addNewAddress(Address address, HttpSession session){
        Integer uid=getuidFromSession(session);
        String  username=getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<>(OK);
    }
    @RequestMapping({"/",""})
    //通过session对象拿到这个uid
    public JsonResult<List<Address>>getByUid(HttpSession session){
        Integer uid = getuidFromSession(session);
        List<Address> list  = addressService.findByUid(uid);
        //把数据传到前端
        return new JsonResult<>(OK,list);

    }
    //RestFul风格写请求路径
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session){
        addressService.setDefault(aid,getuidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid,HttpSession session){
        addressService.delete(aid,
                getuidFromSession(session),
                getUsernameFromSession(session));
        return  new JsonResult<>(OK);
    }

}
