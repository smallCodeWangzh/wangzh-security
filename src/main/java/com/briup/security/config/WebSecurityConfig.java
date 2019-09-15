/**
 * Project Name:auth2
 * File Name:WebSecurityConfig.java
 * Package Name:com.briup.apps.auth2.config
 * Date:2018年9月17日上午10:23:44
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.briup.security.web.filter.JwtAuthenticationTokenFilter;

/**
 * ClassName:WebSecurityConfig <br/>
 * Function: security 配置类 <br/>
 * Date: 2018年9月17日 上午10:23:44 <br/>
 *
 * @author wangzh
 * @version
 * @since JDK 1.8
 * @see
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailServiceImpl")
	private UserDetailsService userDetailService;

	@Bean
	public PasswordEncoder getPasswordEncoderBean() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationTokenFilter getauthenticationTokenFilterBean() {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	public LoginSuccessHandler getLoginSuccessHandler() {
		return new LoginSuccessHandler();
	}

	@Bean
	public LoginFailHandler getLoginFailHandler() {
		return new LoginFailHandler();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.formLogin()
			.loginPage("/authenticaion/login")
			.loginProcessingUrl("/authentication/form")
			.successHandler(getLoginSuccessHandler())
			.failureHandler(getLoginFailHandler())
			.and()
			.csrf().disable() //使用jwt，不需要csrf
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //基于token，不需要session
			.and()
			.authorizeRequests()
			// 设置允许访问的资源
			.antMatchers("/authenticaion/login").permitAll()
			// 设置允许访问的资源
			.antMatchers("/webjars/**").permitAll()
			.antMatchers(
						"/v2/api-docs",
						"/swagger-resources",
						"/swagger-resources/**",
						"/configuration/ui",
						"/configuration/security",
						"/swagger-ui.html/**",
						"/webjars/**"

				).permitAll()
				.anyRequest().authenticated();

		 // 禁用缓存
		http.headers().cacheControl();

		// 添加JWT filter
		http.addFilterBefore(getauthenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}


}
