package com.springdemo.config;

import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.springdemo.web.WebConstants;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	/*
	 * Static URL/resources configuration
	 */

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName(WebConstants.Views.Pages.Misc.MiscHomePage);
		registry.addViewController("/home").setViewName(WebConstants.Views.Pages.Misc.MiscHomePage);
		registry.addViewController("/robots.txt").setViewName(WebConstants.Views.Pages.Misc.MiscRobotsPage);
		registry.addViewController("/infomsg").setViewName(WebConstants.Views.Pages.Common.CommonInfoPage);
		registry.addViewController("/errormsg").setViewName(WebConstants.Views.Pages.Common.CommonErrorPage);
		registry.addViewController("/40x").setViewName(WebConstants.Views.Pages.Error.Error40xPage);
		registry.addViewController("/50x").setViewName(WebConstants.Views.Pages.Error.Error50xPage);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
	}

	/*
	 * Locale configuration
	 */

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName("language");
		localeResolver.setDefaultLocale(Locale.US);
		localeResolver.setDefaultTimeZone(TimeZone.getTimeZone("GMT"));
		return localeResolver;
	}

	/*
	 * Multi-part forms configuration
	 */

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("50Mb");
		factory.setMaxRequestSize("50Mb");
		return factory.createMultipartConfig();
	}

	/*
	 * HttpStatus redirection
	 */

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage page400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/40x");
				ErrorPage page403 = new ErrorPage(HttpStatus.FORBIDDEN, "/40x");
				ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/40x");
				ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/50x");
				ErrorPage page503 = new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "/50x");
				container.addErrorPages(page400, page403, page404, page500, page503);
			}
		};
	}

}
