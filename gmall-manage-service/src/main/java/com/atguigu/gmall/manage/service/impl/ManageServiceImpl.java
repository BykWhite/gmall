package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.mapper.*;
import com.atguigu.gmall.manage.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {


    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Override
    public List<BaseCatalog1> getBaseCatalog1() {
        List<BaseCatalog1> getBaseCatalog1 = baseCatalog1Mapper.selectAll();
        return getBaseCatalog1;
    }

    @Override
    public List<BaseCatalog2> getBaseCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        List<BaseCatalog2> getBaseCatalog2 = baseCatalog2Mapper.select(baseCatalog2);

        return getBaseCatalog2;
    }

    @Override
    public List<BaseCatalog3> getBaseCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        List<BaseCatalog3> getBaseCatalog3 = baseCatalog3Mapper.select(baseCatalog3);
        return getBaseCatalog3;
    }

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.select(baseAttrInfo);
        return baseAttrInfoList;
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        // 说明value_name的值没有拿到！
        // 保存数据：编辑数据放到一起来处理。
        // 是否有主键,操作都是指的是平台属性操作
        if (baseAttrInfo.getId() != null && baseAttrInfo.getId().length() > 0) {
            // 有主键 ，则修改
            baseAttrInfoMapper.updateByPrimaryKey(baseAttrInfo);
        } else {
            // 没有主键则需要添加,注意一下，当没有主键的时候，数据的id要设置成null，如果不设置有可能会出现空字符串
            if (baseAttrInfo.getId().length() == 0) {
                baseAttrInfo.setId(null);
            }
            // 开始插入数据
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        // 操作属性值，先将属性值情况
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);

        // 开始操作属性值列表
        if (baseAttrInfo.getAttrValueList() != null && baseAttrInfo.getAttrValueList().size() > 0) {
            // 循环数据 itar : a-->array iter : each
            for (BaseAttrValue attrValue : baseAttrInfo.getAttrValueList()) {
                // 做插入操作
                if (attrValue.getId().length() == 0) {
                    attrValue.setId(null);
                }
                attrValue.setAttrId(baseAttrInfo.getId());
                /*   baseAttrValueMapper.insertSelective(baseAttrValue);*/
                baseAttrValueMapper.insertSelective(attrValue);
            }
        }
    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        // attrId 实际上是BaseAttrInfo 的id。
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        // 创建BaseAttrValue对象
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        // 将attrId赋值，根据attrId进行查询BaseAttrValue对象
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        // 查询BaseAttrValue的集合
        List<BaseAttrValue> attrValueList = baseAttrValueMapper.select(baseAttrValue);
        // 因为控制器需要的是AttrValueList，所以讲AttrValueList()赋值
        baseAttrInfo.setAttrValueList(attrValueList);
        // 将BaseAttrInfo对象返回
        return baseAttrInfo;
    }

    @Override
    public List<SpuInfo> getSpuInfoList(SpuInfo spuInfo) {
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //保存数据为：spuImg spuInfo spuSaleAttr spuSaleAttrValue
        if (spuInfo.getId() != null && spuInfo.getId().length() > 0) {
            spuInfoMapper.updateByPrimaryKey(spuInfo);
        } else {
            if ( spuInfo.getId()!=null &&spuInfo.getId().length() == 0) {
                spuInfo.setId(null);
            }
            spuInfoMapper.insertSelective(spuInfo);
        }
        //spuImge
        //先删除，在添加
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuInfo.getId());
        spuImageMapper.delete(spuImage);
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();

        for (SpuImage image : spuImageList) {
            //设置Id为NULL，自主镇长
            if(image.getId() != null && image.getId().length() == 0){
                image.setId(null);
        }
        image.setSpuId(spuInfo.getId());
            spuImageMapper.insertSelective(image);
        }
        //属性值。想删除，后插入
        SpuSaleAttr spuSaleAttr = new SpuSaleAttr();
        spuSaleAttr.setSpuId(spuInfo.getId());
        spuSaleAttrMapper.delete(spuSaleAttr);


        SpuSaleAttrValue spuSaleAttrValue = new SpuSaleAttrValue();
        spuSaleAttrValue.setSpuId(spuInfo.getId());
        spuSaleAttrValueMapper.delete(spuSaleAttrValue);

        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr saleAttr : spuSaleAttrList) {
            if(saleAttr.getId() != null && saleAttr.getId().length() == 0){
                saleAttr.setId(null);
            }
            saleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insertSelective(saleAttr);

            List<SpuSaleAttrValue> spuSaleAttrValueList = saleAttr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue saleAttrValue : spuSaleAttrValueList) {
                if(saleAttrValue.getId() != null && saleAttrValue.getId().length() ==0){
                    saleAttrValue.setId(null);
            }
            saleAttrValue.setSpuId(spuInfo.getId());
                spuSaleAttrValueMapper.insertSelective(saleAttrValue);

        }


        }



    }
}