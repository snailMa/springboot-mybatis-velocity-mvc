package com.mhw.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.mhw.example.model.UserIsmBean;
import com.mhw.example.model.UserIsmBeanCriteria;

public interface UserIsmBeanMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int countByExample(UserIsmBeanCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int deleteByExample(UserIsmBeanCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(String userId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int insert(UserIsmBean record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int insertSelective(UserIsmBean record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	List<UserIsmBean> selectByExampleWithRowbounds(UserIsmBeanCriteria example, RowBounds rowBounds);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	List<UserIsmBean> selectByExample(UserIsmBeanCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	UserIsmBean selectByPrimaryKey(String userId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int updateByExampleSelective(@Param("record") UserIsmBean record, @Param("example") UserIsmBeanCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int updateByExample(@Param("record") UserIsmBean record, @Param("example") UserIsmBeanCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(UserIsmBean record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table ism_user
	 *
	 * @mbggenerated
	 */
	int updateByPrimaryKey(UserIsmBean record);
}
