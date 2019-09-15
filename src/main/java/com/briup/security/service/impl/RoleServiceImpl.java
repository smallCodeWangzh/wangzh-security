package com.briup.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.security.bean.Role;
import com.briup.security.mapper.RoleMapper;
import com.briup.security.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public List<Role> findAllByCustomerId(long id) {
		return roleMapper.selectAllByCustomerId(id);
	}

}
