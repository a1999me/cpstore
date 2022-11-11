package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IDistructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IDistructServiceImpl implements IDistructService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        /**
         * 在进行网络数据传输时，为了尽量避免无效数据的传递，可以将无效数据设置成null
         * 可以节省流量，另一方面提升了效率
         */
        List<District> list = districtMapper.findByParent(parent);
        for (District d:list
             ) {
            //已经查过了而且主要是拿code区号，所以id 跟Parent数据就是无效的了
         d.setId(null);
         d.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String Code) {
        String name = districtMapper.findNameByCode(Code);
        return name;
    }
}
