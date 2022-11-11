package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistructService;
import com.cy.store.service.ex.*;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    /**
     * 新增收获地址的实现类
     * @param uid
     * @param username
     * @param address
     */
    @Autowired
    private AddressMapper addressMapper;
    //在添加用户收货地址的业务层依赖与DistrictService的业务层接口
    @Autowired
    private IDistructService districtService;
    @Value("${user.address.max-count}")
    private Integer maxCount;
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //首先查查有没有这个人
        Integer count = addressMapper.countByUid(uid);

        if (count>=maxCount){
            throw new AddressCountLimitException("你写那么多地址干嘛! 难道你家有很多房子？");
        }
        //对address对象中的数据进行补全：省市区的相关数据
        String provinceName=districtService.getNameByCode(address.getProvinceCode());
        String cityName=districtService.getNameByCode(address.getCityCode());
        String areaName=districtService.getNameByCode(address.getAreaCode());
        String allName=provinceName+cityName+areaName;
        System.out.println(allName);
        address.setAddress(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        address.setAddress(allName);

        //uid isDelete\
        address.setUid(uid);
        Integer isDefault=count==0?1:0; //1表示为默认受过地址，0不是默认
        address.setIsDefault(isDefault);
        //补全四项日志
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());
        address.setCreatedUser(username);
        //插入收货地址的方法
        Integer rows=addressMapper.insert(address);
        if (rows!=1){
            throw new InsertException("插入用户的收货地址产生未知的异常");

        }

        }

    @Override
    public List<Address> findByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address:list) {
            //address.setAid(null);
            address.setUid(null);
            //address.setProvinceName(null);
            //address.setProvinceCode(null);
            //address.setCityName(null);
            //address.setCityCode(null);
            //address.setAreaName(null);
            //address.setAreaCode(null);
            address.setZip(null);
            //address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);

        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        //找这个aid是否存在
        Address result = addressMapper.findByAid(aid);
        System.out.println(result.getUid());
        if (result==null){
            throw new AddressNotFoundException("收货地址不存在");
        }
        //检测当前获取到的收货地址的归属
        if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        //把所有的的uid的地址都设置成非默认
        Integer integer = addressMapper.updateNoneDefault(uid);
        if (integer<1){
            throw new UpdateException("更新时产生了未知异常" );
        }
        //把指定的aid更新
        Integer rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows!=1){
            throw new  UpdateException("更新数据产生了异常");
        }

    }

    /**
     * 删除用户的地址
     * @param aid
     * @param uid
     * @param username
     */

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result==null){
            throw new  AddressNotFoundException("地址没找到");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法访问异常");

        }
        Integer rows = addressMapper.deleteByAid(aid);
        //看看删除的时候是否只有一条数据

        if (rows!=1){
            throw new DeleteException("删除数据产生未知的异常");
        }
        Integer count = addressMapper.countByUid(uid);
        if (count==0){
            //直接停止程序
            return;
        }
        //将这条数据中的isDefault的数据改成1
        Address address = addressMapper.findLastModified(uid);
        if (result.getIsDefault()==1){
            rows=addressMapper.updateDefaultByAid(address.getAid(),username,new Date());
        }
        addressMapper.updateDefaultByAid(address.getAid(),username,new Date());
        if (rows!=1){
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }



    @Override
    public Address getByAid(Integer aid,Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if (address==null){
        throw new AddressNotFoundException("地址找不到");
        }if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据方法");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }


    }

