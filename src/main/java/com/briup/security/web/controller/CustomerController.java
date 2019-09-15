package com.briup.security.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.security.bean.Customer;
import com.briup.security.service.ICustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "用户接口")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;
	
	
	@GetMapping("/getCustomer")
	@ApiOperation(value = "根据名字得到用户信息")
	public Customer getCustomerByName(String name) {
		return customerService.findByName(name);
	}

	@GetMapping("/getAllCustomer")
	@ApiOperation(value = "得到所有的用户信息")
	public List<Customer> getAllCustomer() {
		return customerService.findAll();
	}
	
	
}
