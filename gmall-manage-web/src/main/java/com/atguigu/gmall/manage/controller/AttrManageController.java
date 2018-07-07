package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.service.ManageService;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AttrManageController {
    @Reference
    ManageService manageService;

    @RequestMapping("attrListPage")
        public String getAttrListPage(){
        return "attrListPage";
        }
    /**
     * 获得一级分类
     */
    @RequestMapping(value = "getCatalog1" ,method = RequestMethod.POST)
    @ResponseBody
    public List<BaseCatalog1> getCatalog1(){
    List<BaseCatalog1> catalog1List = manageService.getBaseCatalog1();
    return catalog1List;
    }
    /**
     * 获得二级分类
     *
     */
    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        List<BaseCatalog2> catalog2List = manageService.getBaseCatalog2(catalog1Id);
        return catalog2List;
    }
    /**
     * 获得三级分类
     */
    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        List<BaseCatalog3> getCatalog3List = manageService.getBaseCatalog3(catalog2Id);
        return getCatalog3List;
    }
    /**
     *获取属性列表
     */
    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<BaseAttrInfo> attrInfoList(String catalog3Id){
        List<BaseAttrInfo> attrInfoList = manageService.getAttrList(catalog3Id);
        return attrInfoList;
    }
    @RequestMapping(value = "saveAttrInfo",method = RequestMethod.POST)
    @ResponseBody
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo){
        manageService.saveAttrInfo(baseAttrInfo);
    }

    // 返回空没有任何显示的消息
    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<BaseAttrValue> getAttrValueList(String attrId){
//        根据attrId 进行查询

        BaseAttrInfo attrInfo = manageService.getAttrInfo(attrId);
        return  attrInfo.getAttrValueList();
    }
    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> getSaleAttrList(){
        return manageService.getBaseSaleAttrList();

    }
    @RequestMapping(value = "saveSpuInfo",method = RequestMethod.POST)
    @ResponseBody
    public void saveSpuInfo(SpuInfo spuInfo){
        manageService.saveSpuInfo(spuInfo);

    }

}
