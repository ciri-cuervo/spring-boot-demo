package com.example.sbwt.test.config;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;

import com.springdemo.Application;

@Import(Application.class)
@WebAppConfiguration
public class TestConfiguration {

	@Bean
	public ServletContext servletContext() {
		return new MockServletContext();
	}

}
