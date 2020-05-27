package com.java.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.java.gmall.pms.entity.AttrAttrgroupRelation;
import com.java.gmall.pms.service.AttrAttrgroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelation> implements AttrAttrgroupRelationService {

	@Autowired
	private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrAttrgroupRelation> page = this.page(
                new Query<AttrAttrgroupRelation>().getPage(params),
                new QueryWrapper<>()
        );
//		IPage<AttrAttrgroupRelation> page = this.page(new Page<>(1, 5),new QueryWrapper<>());
        return new PageVo(page);
    }

	@Override
	@Transactional
	public void deleteByAttrIdAndAttrGroupId(List<AttrAttrgroupRelation> relations) {
		relations.forEach(relationEntity -> {
			this.remove(new QueryWrapper<AttrAttrgroupRelation>()
					.eq("attr_id", relationEntity.getAttrId())
					.eq("attr_group_id", relationEntity.getAttrGroupId())
			);
		});
	}

	@Override
	public AttrAttrgroupRelation selectById(Long id) {
		return attrAttrgroupRelationDao.selectById(id);
	}

}