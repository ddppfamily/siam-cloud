package com.siam.package_mall.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_mall.entity.PointsMallFullReductionRule;
import com.siam.package_mall.mapper.PointsMallFullReductionRuleMapper;
import com.siam.package_mall.service.PointsMallFullReductionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PointsMallFullReductionRuleServiceImpl implements PointsMallFullReductionRuleService {

    @Autowired
    private PointsMallFullReductionRuleMapper fullReductionRuleMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        fullReductionRuleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(PointsMallFullReductionRule fullReductionRule) {
        fullReductionRule.setCreateTime(new Date());
        fullReductionRuleMapper.insertSelective(fullReductionRule);
    }

    @Override
    public PointsMallFullReductionRule selectByPrimaryKey(Integer id) {
        PointsMallFullReductionRule fullReductionRule = fullReductionRuleMapper.selectByPrimaryKey(id);
        return fullReductionRule;
    }

    @Override
    public void updateByPrimaryKeySelective(PointsMallFullReductionRule fullReductionRule) {
        fullReductionRuleMapper.updateByPrimaryKeySelective(fullReductionRule);
    }

    @Override
    public Page<PointsMallFullReductionRule> getListByPage(int pageNo, int pageSize, PointsMallFullReductionRule fullReductionRule) {
        Page<PointsMallFullReductionRule> page = fullReductionRuleMapper.getListByPage(new Page(pageNo, pageSize), fullReductionRule);
        return page;
    }

    @Override
    public List<PointsMallFullReductionRule> selectByShopId(PointsMallFullReductionRule fullReductionRule) {
        List<PointsMallFullReductionRule> list = fullReductionRuleMapper.getListByPage(new Page(1, 20), fullReductionRule).getRecords();
        return list;
    }
}