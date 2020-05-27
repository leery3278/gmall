package com.java.gmall.pms.service.impl;

import com.java.gmall.pms.dao.SkuInfoDao;
import com.java.gmall.pms.entity.SkuInfo;
import com.java.gmall.pms.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import java.util.List;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfo> implements SkuInfoService {

    @Autowired
    SkuInfoDao skuInfoDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuInfo> page = this.page(
                new Query<SkuInfo>().getPage(params),
                new QueryWrapper<>()
        );
        return new PageVo(page);
    }

    @Override
    public List<SkuInfo> querySkuBySpuId(Long spuId) {
        return skuInfoDao.selectList(new QueryWrapper<SkuInfo>().eq("spu_id",spuId));
    }

}