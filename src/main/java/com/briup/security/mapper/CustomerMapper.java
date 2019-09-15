package com.briup.security.mapper;

import java.util.List;

import com.briup.security.bean.Customer;

public interface CustomerMapper {
	
	/**
	 * 根据名字 查找用户信息
	 * @param name
	 * @return
	 */
	Customer selectByName(String name);
	
	/**
	 * 查询所有的用户信息
	 * @return
	 */
	List<Customer> selectAll();

}
