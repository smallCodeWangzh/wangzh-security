package com.briup.security.service;

import java.util.List;

import com.briup.security.bean.Role;

public interface IRoleService {
	
	List<Role> findAllByCustomerId(long id);

}
