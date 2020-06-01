package com.java.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.dao.CategoryDao;
import com.java.gmall.pms.entity.Category;
import com.java.gmall.pms.service.CategoryService;
import com.java.gmall.pms.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> queryCategoryByPidOrLevel(Long parentCid, Integer level) {
        // 构造查询条件
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        // 如果parentCid为null，说明用户没有传该字段，查询所有
        if (parentCid != null) {
            wrapper.eq("parent_cid", parentCid);
        }
        // 如果level为0，说明查询所有的级别
        if (level != 0) {
            wrapper.eq("cat_level", level);
        }

        return categoryDao.selectList(wrapper);
    }

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<Category> page = this.page(
                new Query<Category>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

    @Override
    public List<CategoryVO> queryCategoryVO(Long pid) {
        return categoryDao.queryCategoryVO(pid);
    }

}