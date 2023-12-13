package com.siam.package_util.service;

import com.siam.package_util.entity.SystemUsageRecord;
import com.siam.package_util.model.example.SystemUsageRecordExample;

import java.util.Date;
import java.util.List;

public interface SystemUsageRecordService {
    int countByExample(SystemUsageRecordExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(SystemUsageRecord record);

    List<SystemUsageRecord> selectByExample(SystemUsageRecordExample example);

    SystemUsageRecord selectByPrimaryKey(Integer id);

    void updateByExampleSelective(SystemUsageRecord record, SystemUsageRecordExample example);

    void updateByPrimaryKeySelective(SystemUsageRecord record);

    /**
     * 查询进店人数
     */
    int selectCountIntoShop(Integer shopId, Date startTime, Date endTime);

    /**
     * 查询进入积分商城人数
     */
    int selectCountIntoPointsMall(Date startTime, Date endTime);
}