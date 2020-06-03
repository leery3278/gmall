package com.java.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.entity.SkuImages;

import java.util.List;


/**
 * sku图片
 *
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
public interface SkuImagesService extends IService<SkuImages> {

    List<SkuImages> querySkuImagesBySkuId(Long skuId);

    PageVo queryPage(QueryCondition params);
}

