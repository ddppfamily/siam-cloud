package com.siam.package_util.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siam.package_util.entity.SysMessage;

/**
 * 系统消息表业务层
 *
 * @author 暹罗
 * @date 2021/10/21 12:37
 */
public interface MessageService extends IService<SysMessage> {

    /**
     * 新增系统消息
     *
     * @author 暹罗
     */
    void insertSelective(SysMessage sysMessage);
}