package com.example.mapper;

import com.example.pojo.magnet_model;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MagnetMapper {
    @Select("select * from magnet_db")
    @ResultMap("BaseResultMap")
    List<magnet_model> selectAll();

    @Select("select * from magnet_db limit #{begin},#{size}")
    @ResultMap("BaseResultMap")
    List<magnet_model> selectByPage(@Param("begin") int begin,@Param("size") int size);


    @Select("select count(*) from magnet_db)")
    int selectTotalCount();
}
