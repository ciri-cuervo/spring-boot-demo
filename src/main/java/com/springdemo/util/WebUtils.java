package com.springdemo.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.springdemo.domain.User;

public class WebUtils {

	public static final String HASH_VALID_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz";
	public static final int HASH_LENGTH = 24;

	public static final String REGEX_DOMAIN_PATTERN = "((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}";

	public static final String REGEX_USERNAME_PATTERN = "^[a-z0-9\\-]{4,32}$";
	public static final String REGEX_EMAIL_PATTERN = "^(?!.{255})[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ REGEX_DOMAIN_PATTERN + "$";
	public static final String REGEX_PASSWORD_PATTERN = "^(?=(.*[A-Za-z]){1,})(?=(.*[0-9]){1,}).{6,32}$";
	public static final String REGEX_HASH_PATTERN = "^[" + HASH_VALID_CHARACTERS + "]{"
			+ HASH_LENGTH + "," + HASH_LENGTH + "}$";

	public static final int HTTP_DEF_PORT = 80;
	public static final int HTTPS_DEF_PORT = 443;

	/* Authentication */

	public static User getAuthenticatedUser()
	{ // TODO check rememberme's principal class (is it User?)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof User)
		{
			return (User) authentication.getPrincipal();
		}
		return null;
	}

	public static boolean isAnonymous()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication instanceof AnonymousAuthenticationToken;
	}

	public static boolean isRememberMeAuthenticated()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication instanceof RememberMeAuthenticationToken;
	}

	public static boolean isAuthenticated()
	{
		return !isAnonymous();
	}

	public static boolean isFullyAuthenticated()
	{
		return !isAnonymous() && !isRememberMeAuthenticated();
	}

	/* Other services */

	public static String getBaseUrl(HttpServletRequest request)
	{
		if (request.getServerPort() != HTTP_DEF_PORT && request.getServerPort() != HTTPS_DEF_PORT)
		{
			// http://domain.com:8080
			if ("/".equals(request.getContextPath()))
			{
				return String.format("%s://%s:%d", request.getScheme(), request.getServerName(),
						request.getServerPort());
			}
			// http://domain.com:8080/contextpath
			return String.format("%s://%s:%d%s", request.getScheme(), request.getServerName(),
					request.getServerPort(), request.getContextPath());
		}
		// http://domain.com
		if ("/".equals(request.getContextPath()))
		{
			return String.format("%s://%s", request.getScheme(), request.getServerName());
		}
		// http://domain.com/contextpath
		return String.format("%s://%s%s", request.getScheme(), request.getServerName(),
				request.getContextPath());
	}

	public static String createHash()
	{
		return RandomStringUtils.random(HASH_LENGTH, HASH_VALID_CHARACTERS);
	}

	private WebUtils() {
	}

}
