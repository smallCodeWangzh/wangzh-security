package com.briup.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.briup.security.bean.Customer;
import com.briup.security.bean.Role;
import com.briup.security.service.ICustomerService;
import com.briup.security.service.IRoleService;

/**
 * 
 * <p>
 * 		security 自定义登陆逻辑类
 * 		用来做登陆认证，验证用户名与密码
 * </p>
 * 
 * @author wangzh
 *
 */
@Component("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 根据用户名去查找用户信息
		Customer customer = customerService.findByName(username);
		
		if(customer == null) {
			throw new UsernameNotFoundException(String.format("Not user Found with '%s'", username));
		}

		// 根据用户id查询角色
		List<Role> roles = roleService.findAllByCustomerId(customer.getId());

		return new User(customer.getName(),passwordEncoder.encode(customer.getPassword()),getGrantedAuthority(roles));
	}

	/*** 
	 * @Description: 获取角色权限
	 * @Param: [roles] 
	 * @return: java.util.List<org.springframework.security.core.GrantedAuthority> 
	 * @Author: wangzh
	 * @Date: 2019/3/21 
	 */
	private List<GrantedAuthority> getGrantedAuthority(List<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>(roles.size());

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}


}
