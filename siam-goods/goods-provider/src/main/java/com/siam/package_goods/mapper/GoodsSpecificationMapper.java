package com.siam.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.package_goods.entity.GoodsSpecification;
import com.siam.package_goods.model.example.GoodsSpecificationExample;
import com.siam.package_goods.model.dto.GoodsSpecificationDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface GoodsSpecificationMapper extends BaseMapper<GoodsSpecification> {
    int countByExample(GoodsSpecificationExample example);

    int deleteByExample(GoodsSpecificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(GoodsSpecification record);

    List<GoodsSpecification> selectByExample(GoodsSpecificationExample example);

    GoodsSpecification selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsSpecification record, @Param("example") GoodsSpecificationExample example);

    int updateByExample(@Param("record") GoodsSpecification record, @Param("example") GoodsSpecificationExample example);

    int updateByPrimaryKeySelective(GoodsSpecification record);

    int updateByPrimaryKey(GoodsSpecification record);

    @ResultMap("BaseResultMap")
    @Select("<script>select s.* from tb_goods_specification s " +
            "<where> 1=1 " +
            "<if test=\"goodsSpecification.id != null\"> AND s.id = #{goodsSpecification.id} </if>" +
            "<if test=\"goodsSpecification.goodsId != null\"> AND s.goods_id = #{goodsSpecification.goodsId} </if>" +
            "<if test=\"goodsSpecification.name != null and goodsSpecification.name !=''\"> AND s.name like '%${goodsSpecification.name}%' </if>" +
            "<if test=\"goodsSpecification.sortNumber != null\"> AND s.sort_number = #{goodsSpecification.sortNumber} </if>" +
            "</where> order by s.id asc" +
            "</script>")
    Page<GoodsSpecification> getListByPage(@Param("page") Page page, @Param("specList") GoodsSpecification goodsSpecification);

    @ResultMap("CustomResultMap")
    @Select("<script>select s.*, g.name AS goodsName, g.main_image AS goodsMainImage from tb_goods_specification s " +
            "LEFT JOIN tb_goods g ON g.id = s.goods_id " +
            "<where> 1=1 " +
            "<if test=\"goodsSpecificationDto.id != null\"> AND s.id = #{goodsSpecificationDto.id} </if>" +
            "<if test=\"goodsSpecificationDto.goodsId != null\"> AND s.goods_id = #{goodsSpecificationDto.goodsId} </if>" +
            "<if test=\"goodsSpecificationDto.name != null and goodsSpecificationDto.name !=''\"> AND s.name like '%${goodsSpecificationDto.name}%' </if>" +
            "<if test=\"goodsSpecificationDto.sortNumber != null\"> AND s.sort_number = #{goodsSpecificationDto.sortNumber} </if>" +
            "<if test=\"goodsSpecificationDto.goodsName != null and goodsSpecificationDto.goodsName !=''\"> AND g.name like '%${goodsSpecificationDto.goodsName}%' </if>" +
            "<if test=\"goodsSpecificationDto.goodsMainImage != null and goodsSpecificationDto.goodsMainImage !=''\"> AND g.main_image like '%${goodsSpecificationDto.goodsMainImage}%' </if>" +
            "</where> order by g.id desc, s.sort_number asc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinGoods(@Param("goodsSpecificationDto") GoodsSpecificationDto goodsSpecificationDto);

    @Select("select IFNULL(max(s.sort_number), 0) from tb_goods_specification s where s.goods_id = #{goodsId}")
    int selectMaxSortNumberByGoodsId(@Param("goodsId") Integer goodsId);

    @Select("select s.* from tb_goods_specification s where s.goods_id = #{goodsId} and s.name = #{name}")
    GoodsSpecification selectByGoodsIdAndName(@Param("goodsId") Integer goodsId, @Param("name") String name);

    @Delete("delete from tb_goods_specification where goods_id = #{goodsId}")
    void deleteByGoodsId(@Param("goodsId") int goodsId);
}