package com.briup.security.service;

import java.util.List;

import com.briup.security.bean.Customer;

public interface ICustomerService {
	
	/**
	 * 根据 名字查询
	 * @param name
	 * @return
	 */
	Customer findByName(String name);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<Customer> findAll();

}
