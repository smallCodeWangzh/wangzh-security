package com.briup.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.security.bean.Customer;
import com.briup.security.mapper.CustomerMapper;
import com.briup.security.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public Customer findByName(String name) {
		return customerMapper.selectByName(name);
	}

	@Override
	public List<Customer> findAll() {
		return customerMapper.selectAll();
	}

}
