package com.hr.api.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	private static final String STATIC_PATH = "src/main/resources/static/"; 
	
	private static final Set<String> PDF_WORD_TYPES = Set.of(
		    "application/pdf",
		    "application/msword",                    
		    "application/vnd.openxmlformats-officedocument.wordprocessingml.document" 
		);
	
	public static String upload(String path, MultipartFile file) {
		
		try {
		 	String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
	        
		 	Path uploadPath = Paths.get(STATIC_PATH + path);
	        
	        Files.createDirectories(uploadPath);
	        
	        Path filePath = uploadPath.resolve(fileName);
	        
	        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	        
		return path + fileName;
	   
		} catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
       }
	}
	
	public static void upload(String path, MultipartFile file, Consumer<String> callback) {
		
		String imagePath = upload(path, file);
		
		callback.accept(imagePath);
	}
	
	
	public static String uploadImage(String path, MultipartFile file) {
		
		throwIfNotImage(file);
		return upload(path, file);
	}
	
	
	public static void uploadImage(String path, MultipartFile file, Consumer<String> callback) {
		
		throwIfNotImage(file);
		upload(path, file, callback);
	}
	
	public static void uploadPdfOrWord(String path, MultipartFile file, Consumer<String> callback) {
		
		throwIfNotPdfOrWord(file);
		upload(path, file, callback);
	}
	
	private static void throwIfNotPdfOrWord(MultipartFile file) {

		if(!isPdfOrWord(file)) {
			 throw new IllegalArgumentException("{validation.onlyPdfOrWord}");
		}
	}

	public static void throwIfNotImage(MultipartFile file) {
		
		if (!isImageFile(file)) {
		    throw new IllegalArgumentException("{validation.onlyImage}");
		}
	}
	
	
	public static boolean isImageFile(MultipartFile file) {
		
		return file.getContentType().startsWith("image/");
	}
	
	public static boolean isPdfOrWord(MultipartFile file) {
		
	    return file.getContentType() != null && PDF_WORD_TYPES.contains(file.getContentType());	
	}

	public static void deleteFile(String path) {
		 try {
		 
		 	Path filePath = Paths.get(STATIC_PATH + path);
	        Files.delete(filePath);
		  
		 } catch (NoSuchFileException e) {
		        
		    
		 } catch (IOException e) {
		        System.out.println("Error deleting file: " + e.getMessage());
		 }
	}
	
	
	
}
