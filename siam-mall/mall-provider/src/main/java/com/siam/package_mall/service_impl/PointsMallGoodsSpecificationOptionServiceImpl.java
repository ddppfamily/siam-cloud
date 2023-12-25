package com.siam.package_mall.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_mall.mapper.PointsMallGoodsSpecificationOptionMapper;
import com.siam.package_mall.service.PointsMallGoodsSpecificationOptionService;
import com.siam.package_mall.model.dto.PointsMallGoodsSpecificationOptionDto;
import com.siam.package_mall.entity.PointsMallGoodsSpecificationOption;
import com.siam.package_mall.model.example.PointsMallGoodsSpecificationOptionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PointsMallGoodsSpecificationOptionServiceImpl implements PointsMallGoodsSpecificationOptionService {

    @Autowired
    private PointsMallGoodsSpecificationOptionMapper goodsSpecificationOptionMapper;

    public int countByExample(PointsMallGoodsSpecificationOptionExample example){
        return goodsSpecificationOptionMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        goodsSpecificationOptionMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(PointsMallGoodsSpecificationOption record){
        goodsSpecificationOptionMapper.insertSelective(record);
    }

    public List<PointsMallGoodsSpecificationOption> selectByExample(PointsMallGoodsSpecificationOptionExample example){
        return goodsSpecificationOptionMapper.selectByExample(example);
    }

    public PointsMallGoodsSpecificationOption selectByPrimaryKey(Integer id){
        return goodsSpecificationOptionMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(PointsMallGoodsSpecificationOption record, PointsMallGoodsSpecificationOptionExample example){
        goodsSpecificationOptionMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(PointsMallGoodsSpecificationOption record){
        goodsSpecificationOptionMapper.updateByPrimaryKeySelective(record);
    }

    public Page<PointsMallGoodsSpecificationOption> getListByPage(int pageNo, int pageSize, PointsMallGoodsSpecificationOption goodsSpecificationOption) {
        Page<PointsMallGoodsSpecificationOption> page = goodsSpecificationOptionMapper.getListByPage(new Page(pageNo, pageSize), goodsSpecificationOption);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinPointsMallGoods(int pageNo, int pageSize, PointsMallGoodsSpecificationOptionDto goodsSpecificationOptionDto) {
        Page<Map<String, Object>> page = goodsSpecificationOptionMapper.getListByPageJoinPointsMallGoods(new Page(pageNo, pageSize), goodsSpecificationOptionDto);
        return page;
    }

    @Override
    public int selectMaxSortNumberByPointsMallGoodsSpecificationId(Integer specificationId) {
        return goodsSpecificationOptionMapper.selectMaxSortNumberByPointsMallGoodsSpecificationId(specificationId);
    }

    @Override
    public BigDecimal selectSumPriceByGoodsIdAndName(Integer goodsId, List<String> nameList) {
        return goodsSpecificationOptionMapper.selectSumPriceByGoodsIdAndName(goodsId, nameList);
    }

//    @Override
//    public void updateByPointsMallGoodsAccessories(PointsMallGoodsAccessories goodsAccessories) {
//        goodsSpecificationOptionMapper.updateByPointsMallGoodsAccessories(goodsAccessories);
//    }

    @Override
    public void deleteByPointsMallGoodsId(int goodsId) {
        goodsSpecificationOptionMapper.deleteByPointsMallGoodsId(goodsId);
    }
}