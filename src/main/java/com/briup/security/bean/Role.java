package com.briup.security.bean;

import java.io.Serializable;

public class Role implements Serializable {

	private static final long serialVersionUID = -2158194219185524323L;
	
	private long id;
	private String name;

	private Customer customer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
