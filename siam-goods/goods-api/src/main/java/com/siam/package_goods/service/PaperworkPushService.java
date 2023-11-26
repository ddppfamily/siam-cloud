package com.siam.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_goods.entity.PaperworkPush;

/**
 *  jianyang
 */
public interface PaperworkPushService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PaperworkPush paperworkPush);

    PaperworkPush selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(PaperworkPush paperworkPush);

    /**
     * 条件查询集合
     * @param paperworkPush 查询条件对象
     * @param pageNo 分页所在页
     * @param pageSize 单页数据量大小
     * @return
     */
    Page getList(int pageNo, int pageSize, PaperworkPush paperworkPush);
}