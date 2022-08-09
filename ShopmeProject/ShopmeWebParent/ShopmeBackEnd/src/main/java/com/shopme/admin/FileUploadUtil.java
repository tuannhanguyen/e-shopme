package com.shopme.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;


public class FileUploadUtil {

	public static void saveFile(String uploadDir, String fileName,
				MultipartFile multipartFile) throws IOException {
		
		Path uploadPath = Paths.get(uploadDir);
		// neu khong ton tai, tao thu muc moi
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (Exception e) {
			throw new IOException("Could not save file: " + fileName,e);
		} 
	}
	
	public static void cleanDir(String dir) {
		Path dirPath = Paths.get(dir);
		
		try {
			Files.list(dirPath).forEach(file ->{ 
				// Tests whether the file denoted by this pathname is a directory.
				if(!Files.isDirectory(file)) {
						try {
							Files.delete(file);
						}catch (Exception e) {
							System.out.println("Could not delete file " + file);
						}
					}
				});
		} catch (IOException e) {
			System.out.println("Could not delete Directory " + dirPath);
		}
	}
	
	public static void removeDir(String dir) {
		cleanDir(dir);
		
		try {
			Files.delete(Paths.get(dir));
		} catch (IOException e) {
			
		}
	}
}
