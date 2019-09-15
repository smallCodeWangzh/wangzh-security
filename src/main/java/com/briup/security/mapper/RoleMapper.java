package com.briup.security.mapper;

import java.util.List;

import com.briup.security.bean.Role;

public interface RoleMapper {

	/**
	 * 根据用户id查询用户所有的角色
	 * @param customerId
	 * @return
	 */
	List<Role> selectAllByCustomerId(long customerId);
	
}
