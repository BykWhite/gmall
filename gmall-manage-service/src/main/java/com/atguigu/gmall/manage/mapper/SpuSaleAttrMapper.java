package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr>{
    //根据spuId查询属性集合
    public List<SpuSaleAttr> selectSpuSaleAttrList(long spuId);
}
