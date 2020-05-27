package com.java.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.java.gmall.pms.dao.AttrDao;
import com.java.gmall.pms.dao.AttrGroupDao;
import com.java.gmall.pms.entity.Attr;
import com.java.gmall.pms.entity.AttrAttrgroupRelation;
import com.java.gmall.pms.entity.AttrGroup;
import com.java.gmall.pms.service.AttrGroupService;
import com.java.gmall.pms.vo.GroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroup> implements AttrGroupService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroup> page = this.page(
                new Query<AttrGroup>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryGroupByPage(QueryCondition queryCondition, Long catId) {
        IPage<AttrGroup> page = this.page(
                new Query<AttrGroup>().getPage(queryCondition),
                new QueryWrapper<AttrGroup>().eq("catelog_id", catId)
        );

        return new PageVo(page);
    }

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

	@Autowired
	private AttrDao attrDao;

    @Override
    public GroupVO queryGroupByGid(Long gid) {
        // 查询分组
        GroupVO attrGroupVO = new GroupVO();
        AttrGroup attrGroup = attrGroupDao.selectById(gid);
        BeanUtils.copyProperties(attrGroup, attrGroupVO);

        // 查询分组下的关联关系
        List<AttrAttrgroupRelation> relations = attrAttrgroupRelationDao.selectList(new
                QueryWrapper<AttrAttrgroupRelation>().eq("attr_group_id", gid));
        // 判断关联关系是否为空，如果为空，直接返回
        if (CollectionUtils.isEmpty(relations)) {
            return attrGroupVO;
        }
        attrGroupVO.setRelations(relations);
        // 收集分组下的所有规格id
		List<Long> attrIds = new ArrayList<>();
		for(AttrAttrgroupRelation relation:relations) {
			Long attrId = relation.getAttrId();
			attrIds.add(attrId);
		}
		// 查询分组下的所有规格参数
		List<Attr> attrEntities = attrDao.selectBatchIds(attrIds);
		attrGroupVO.setAttrEntities(attrEntities);
        return attrGroupVO;
    }

    @Override
    public List<GroupVO> queryByCatId(Long catId) {
        // 查询所有的分组
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("catelog_id",catId);
        List<AttrGroup> attrGroups = attrGroupDao.selectList(wrapper);
        // 查询出每组下的规格参数
        List<GroupVO> groupVOList = new ArrayList<>();
        for(GroupVO groupVO:groupVOList) {
            for(AttrGroup attrGroup:attrGroups) {
                BeanUtils.copyProperties(attrGroup, groupVO);
                // 查询分组下的关联关系
                List<AttrAttrgroupRelation> relations = attrAttrgroupRelationDao.selectList(new
                        QueryWrapper<AttrAttrgroupRelation>().
                        eq("attr_group_id", attrGroup.getAttrGroupId()));
                // 收集分组下的所有规格id
                List<Long> attrIds = new ArrayList<>();
                for(AttrAttrgroupRelation relation:relations) {
                    Long attrId = relation.getAttrId();
                    attrIds.add(attrId);
                }
                // 查询分组下的所有规格参数
                List<Attr> attrEntities = attrDao.selectBatchIds(attrIds);
                groupVO.setAttrEntities(attrEntities);
            }
        }
        return groupVOList;

    }

}