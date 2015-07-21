package com.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.springdemo.service.DefaultUserDetailsService;
import com.springdemo.spring.AuthenticationManagerImpl;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DefaultUserDetailsService userDetailsService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return new AuthenticationManagerImpl(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/infomsg").permitAll()
				.antMatchers("/errormsg").permitAll()
				.antMatchers("/account/login").permitAll()
				.antMatchers("/account/signup").anonymous()
				.antMatchers("/account/activation").permitAll()
				.antMatchers("/account/password/reset").anonymous()
				.antMatchers(HttpMethod.GET, "/account/password/reset/confirm").permitAll()
				.antMatchers(HttpMethod.POST, "/account/password/reset/confirm").anonymous();

		http.formLogin()
				.loginPage("/account/login")
				.failureUrl("/account/login?error");

//        http.authorizeRequests().anyRequest().authenticated()
//            .and()
//            .formLogin().loginPage("/loginPage").successHandler(new DbsAuthenticationSuccessHandler()).failureUrl("/loginPage?error")
//            .and()
//            .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/loginPage?logout")
//            .and()
//            .rememberMe().key("rememberme").useSecureCookie(true)
//            .and()
//            .exceptionHandling().accessDeniedPage("/403");
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/static/**")
			.antMatchers("/favicon.ico")
			.antMatchers("/robots.txt");
	}

}
