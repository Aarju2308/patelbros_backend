package com.patelbros.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
	
	private final String filePath = "./uploads";
	
	public String saveFile(MultipartFile file,String folder) {
		
		String finalPath = filePath + File.separator + folder;
	
		File targetFolder = new File(finalPath);
		
		if (!targetFolder.exists()) {
			boolean makeFolder = targetFolder.mkdirs();
			if (!makeFolder) {
				log.warn("Can not create Folder");
				return "";
			}
		}
		
		String fileExtension = getFileExtension(file.getOriginalFilename());
		String targetFilePath = finalPath + File.separator + System.currentTimeMillis() + "." + fileExtension;
		Path targetPath = Paths.get(targetFilePath);
		
		try {
			Files.write(targetPath, file.getBytes());
			log.info("Saved file at " + targetFilePath);
			return targetFilePath;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
		return null;
		
	}

	private String getFileExtension(String originalFilename) {
		 int lastDotIndex = originalFilename.lastIndexOf('.');
		    if (lastDotIndex == -1) {
		        return ""; 
		    }
		    return originalFilename.substring(lastDotIndex + 1).toLowerCase();
	}

}
