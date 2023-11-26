package com.siam.package_goods.model.dto;

import com.siam.package_goods.entity.Goods;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class GoodsMenuDto extends Goods {

    @ApiModelProperty(notes = "菜单id")
    private Integer menuId;

    @ApiModelProperty(notes = "菜单名称")
    private String menuName;

    @ApiModelProperty(notes = "指定查询状态为 2=已上架 4=售罄 的商品")
    private Boolean goodsStatusIn2And4;

    @ApiModelProperty(notes = "开始时间")
    private Date startTime;

    @ApiModelProperty(notes = "结束时间")
    private Date endTime;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Boolean getGoodsStatusIn2And4() {
        return goodsStatusIn2And4;
    }

    public void setGoodsStatusIn2And4(Boolean goodsStatusIn2And4) {
        this.goodsStatusIn2And4 = goodsStatusIn2And4;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}