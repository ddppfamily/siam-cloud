package com.siam.package_merchant.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_merchant.mapper.ShopChangeRecordMapper;
import com.siam.package_merchant.service.ShopChangeRecordService;
import com.siam.package_merchant.entity.ShopChangeRecord;
import com.siam.package_merchant.model.example.ShopChangeRecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShopChangeRecordServiceImpl implements ShopChangeRecordService {

    @Autowired
    private ShopChangeRecordMapper shopChangeRecordMapper;

    @Override
    public int countByExample(ShopChangeRecordExample example) {
        return shopChangeRecordMapper.countByExample(example);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        shopChangeRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(ShopChangeRecord shopChangeRecord) {
        shopChangeRecordMapper.insertSelective(shopChangeRecord);
    }

    @Override
    public ShopChangeRecord selectByPrimaryKey(Integer id) {
        return shopChangeRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(ShopChangeRecord shopChangeRecord) {
        shopChangeRecordMapper.updateByPrimaryKeySelective(shopChangeRecord);
    }

    @Override
    public Page getListByPage(int pageNo, int pageSize, ShopChangeRecord shopChangeRecord) {
        Page<Map<String, Object>> page = shopChangeRecordMapper.getListByPage(new Page(pageNo, pageSize), shopChangeRecord);
        return page;
    }

    @Override
    public Page getListByPageJoinShop(int pageNo, int pageSize, ShopChangeRecord shopChangeRecord) {
        Page<Map<String, Object>> page = shopChangeRecordMapper.getListByPageJoinShop(new Page(pageNo, pageSize), shopChangeRecord);
        return page;
    }

    @Override
    public ShopChangeRecord selectLastestByShopId(Integer shopId) {
        return shopChangeRecordMapper.selectLastestByShopId(shopId);
    }
}