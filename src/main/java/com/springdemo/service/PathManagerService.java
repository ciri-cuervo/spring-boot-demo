package com.springdemo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springdemo.domain.User;

@Service
public class PathManagerService {

	private static final String TEMP_DIR = "tmp";
	private static final String USERS_DIR = "users";

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private Environment environment;

	public Path getTempStoragePath()
	{
		Path tempStoragePath = null;
		String tempStorage = environment.getProperty("webapp.tmpstorage");
		if (StringUtils.hasText(tempStorage))
		{
			tempStoragePath = Paths.get(tempStorage);
		}
		if (tempStoragePath == null || !Files.isWritable(tempStoragePath))
		{
			tempStoragePath = Paths.get(servletContext.getRealPath("/"), TEMP_DIR);
		}
		return tempStoragePath;
	}

	public Path getDefaultStoragePath()
	{
		Path defaultStoragePath = null;
		String defaultStorage = environment.getProperty("webapp.defaultstorage");
		if (StringUtils.hasText(defaultStorage))
		{
			defaultStoragePath = Paths.get(defaultStorage);
		}
		if (defaultStoragePath == null || !Files.isWritable(defaultStoragePath))
		{
			defaultStoragePath = Paths.get(servletContext.getRealPath("/"));
		}
		return defaultStoragePath;
	}

	public Path getUsersBasePath() throws IOException
	{
		Path defaultStoragePath = getDefaultStoragePath();
		Path usersBasePath = defaultStoragePath.resolve(USERS_DIR);
		return Files.createDirectories(usersBasePath);
	}

	public Path getUserBasePath(User user) throws IOException
	{
		Path usersBasePath = getUsersBasePath();
		Path userBasePath = usersBasePath.resolve(user.getId().toString());
		return Files.createDirectories(userBasePath);
	}

}
