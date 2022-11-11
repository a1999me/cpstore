package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

public interface DistrictMapper {
    //查询到的结果可能是多个,所以使用List，根据父代号查询区域信息，
    // 查询某个父区域下的所有区域列表
    List<District> findByParent(String parent);
    String findNameByCode(String code);
}
