package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

public interface IDistructService {
    /**
     * 调用mapper层
     * 根据父代号来查询区域信息
     */
   List<District> getByParent(String parent);
   String  getNameByCode(String Code);

}
