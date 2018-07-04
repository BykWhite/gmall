package com.atguigu.gmall.manage.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface ManageService {
    public List<BaseCatalog1> getBaseCatalog1();
    public List<BaseCatalog2> getBaseCatalog2(String catalog1Id);
    public List<BaseCatalog3> getBaseCatalog3(String catalog2Id);
    public List<BaseAttrInfo> getAttrList(String catalog3Id);
    // 保存数据方法
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValueList(String attrId);

}