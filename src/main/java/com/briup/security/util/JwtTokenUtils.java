package com.briup.security.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author : wangzh
 * @version V1.0
 * @Description: jwt工具类
 */
public class JwtTokenUtils {

	public static final String TOKEN_HEADER = "Authorization";

	public static final String TOKEN_PREFIX = "Bearer ";
	/**
	 * 密钥key
	 */
	private static final String SECRET = "jwtsecurit";

	/**
	 * JWT的发行人
	 */
	private static final String ISS = "Kunshan Briup";

	/**
	 * 自定义用户信息
	 */
	private static final String ROLE_CLAIMS = "rol";

	/**
	 * 过期时间是3600秒，既是1个小时
	 */
	public static final long EXPIRATION = 3600L * 1000;

	/**
	 * 选择了记住我之后的过期时间为7天
	 */
	public static final long EXPIRATION_REMEMBER = 604800L * 1000;

	/**
	 * 创建token
	 * 
	 * @param username
	 *            登录名
	 * @param roles
	 *            用户角色信息
	 * @param isRememberMe
	 *            是否记住我
	 * @return
	 */
	public static String createToken(UserDetails details, boolean isRememberMe) throws CustomerException {
		// 如果选择记住我，则token的过期时间为
		long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;

		HashMap<String, Object> map = new HashMap<>();

		map.put(ROLE_CLAIMS, details.getAuthorities()); // 角色名字
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET) // 加密算法
				.setClaims(map) // 自定义信息
				.setIssuer(ISS) // jwt发行人
				.setSubject(details.getUsername()) // jwt面向的用户
				.setIssuedAt(new Date()) // jwt发行人
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // key过期时间
				.compact();
	}

	/**
	 * 从token获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) throws CustomerException {
		return getTokenBody(token).getSubject();
	}

	/**
	 * 从token中获取用户角色
	 * 
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getUserRole(String token) throws CustomerException {
		List<GrantedAuthority> userAuthorities = (List<GrantedAuthority>) getTokenBody(token).get(ROLE_CLAIMS);
		return AuthorityUtils.authorityListToSet(userAuthorities);
	}

	/**
	 * 是否已过期
	 * 
	 * @param token
	 * @return
	 */
	public static boolean isExpiration(String token) throws CustomerException {
		return getTokenBody(token).getExpiration().before(new Date());
	}

	private static Claims getTokenBody(String token) throws CustomerException {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	/**
	 * 验证token
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public static boolean validateToken(String token, UserDetails userDetails) throws CustomerException {
		User user = (User) userDetails;
		final String username = getUsername(token);
		return (username.equals(user.getUsername()) && isExpiration(token) == false);
	}

}
