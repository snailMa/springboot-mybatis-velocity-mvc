package com.mhw.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhw.example.dao.UserIsmBeanMapper;
import com.mhw.example.model.UserIsmBean;
import com.mhw.example.service.UserIsmService;

@Service
public class UserIsmServiceImpl implements UserIsmService {

	@Autowired
	UserIsmBeanMapper userDao;

	@Override
	public UserIsmBean getByUserId(String userId) {
		return userDao.selectByPrimaryKey(userId);
	}

}
