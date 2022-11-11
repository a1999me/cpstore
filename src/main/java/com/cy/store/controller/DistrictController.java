package com.cy.store.controller;

import com.cy.store.entity.District;
import com.cy.store.service.IDistructService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {
    @Autowired
    private IDistructService distructService;
    //districts开头的请求都被拦截到parent方法里了，不需要再进行细分
    @RequestMapping("/")
    public JsonResult<List<District>> getBYParent(String parent){
        List<District> data = distructService.getByParent(parent);
        return new JsonResult<>(OK,data);
    }
}
