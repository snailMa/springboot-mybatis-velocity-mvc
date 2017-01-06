package com.mhw.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mhw.example.model.UserIsmBean;

@Mapper
public interface TestMapper {

	@Select("select * from ism_user where user_id = #{userId}")
	UserIsmBean selectByUserId(@Param("userId") String userId);

}
