package com.java.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.gmall.pms.dao.SkuImagesDao;
import com.java.gmall.pms.entity.SkuImages;
import com.java.gmall.pms.service.SkuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import java.util.List;


@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesDao, SkuImages> implements SkuImagesService {

    @Autowired
    private SkuImagesDao skuImagesDao;

    @Override
    public List<SkuImages> querySkuImagesBySkuId(Long skuId) {
        return skuImagesDao.selectList(new QueryWrapper<SkuImages>().eq("sku_id",skuId));
    }

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuImages> page = this.page(
                new Query<SkuImages>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}