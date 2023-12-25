package com.siam.package_mall.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_mall.mapper.PointsMallMenuGoodsRelationMapper;
import com.siam.package_mall.service.PointsMallMenuGoodsRelationService;
import com.siam.package_mall.entity.PointsMallMenuGoodsRelation;
import com.siam.package_mall.model.example.PointsMallMenuGoodsRelationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PointsMallMenuGoodsRelationServiceImpl implements PointsMallMenuGoodsRelationService {

    @Autowired
    private PointsMallMenuGoodsRelationMapper menuPointsMallGoodsRelationMapper;

    @Override
    public int countByExample(PointsMallMenuGoodsRelationExample example){
        return menuPointsMallGoodsRelationMapper.countByExample(example);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        menuPointsMallGoodsRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(PointsMallMenuGoodsRelation menuPointsMallGoodsRelation) {
        menuPointsMallGoodsRelationMapper.insertSelective(menuPointsMallGoodsRelation);
    }

    @Override
    public PointsMallMenuGoodsRelation selectByPrimaryKey(Integer id) {
        return menuPointsMallGoodsRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(PointsMallMenuGoodsRelation menuPointsMallGoodsRelation) {
        menuPointsMallGoodsRelationMapper.updateByPrimaryKeySelective(menuPointsMallGoodsRelation);
    }

    @Override
    public Page getListByPage(int pageNo, int pageSize, PointsMallMenuGoodsRelation menuPointsMallGoodsRelation) {
        Page<Map<String, Object>> page = menuPointsMallGoodsRelationMapper.getListByPage(new Page(pageNo, pageSize), menuPointsMallGoodsRelation);
        return page;
    }

    @Override
    public List<PointsMallMenuGoodsRelation> selectByExample(PointsMallMenuGoodsRelationExample example){
        return menuPointsMallGoodsRelationMapper.selectByExample(example);
    }

    @Override
    public void deleteByPointsMallGoodsId(int goodsId) {
        menuPointsMallGoodsRelationMapper.deleteByPointsMallGoodsId(goodsId);
    }
}