package com.patelbros.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUtils {

	public byte[] readFile(String filePath) {
		if (filePath == null || filePath.isEmpty()) {
			log.info("Filepath is Empty");
			return null;
		}
		try {
			Path path = new File(filePath).toPath();
			return Files.readAllBytes(path);
		} catch (IOException e) {
			log.warn("No File found at path : " + filePath);
		}
		return null;
		
	}
}
