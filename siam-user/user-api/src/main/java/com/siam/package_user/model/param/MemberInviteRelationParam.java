package com.siam.package_user.model.param;

import com.siam.package_user.entity.MemberInviteRelation;
import lombok.Data;

/**
 * 用户账单记录表
 *
 * @author 暹罗
 */
@Data
public class MemberInviteRelationParam extends MemberInviteRelation {

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;
}