package com.shopme;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		String dirName = "users-photos";
//		Path userPhotosDir = Paths.get(dirName);
//		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
//		registry.addResourceHandler("/" + dirName + "/**")
//			.addResourceLocations("file:/" + userPhotosPath + "/");
//		
//		String categoryImageDirName = "../category-images";
//		Path categoryImagesDir = Paths.get(categoryImageDirName);
//		String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();
//		registry.addResourceHandler("/category-images/**")
//			.addResourceLocations("file:/" + categoryImagesPath + "/");
//		
//		String brandImageDirName = "../brand-logos";
//		Path brandImagesDir = Paths.get(brandImageDirName);
//		String brandImagesPath = brandImagesDir.toFile().getAbsolutePath();
//		registry.addResourceHandler("/brand-logos/**")
//			.addResourceLocations("file:/" + brandImagesPath + "/");
		
		exposeDirectory("../category-images", registry);
		exposeDirectory("../brand-logos", registry);
		exposeDirectory("../product-images", registry);
		exposeDirectory("../site-logo", registry);
	}

	private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
		Path path = Paths.get(pathPattern);
		String absolutePath = path.toFile().getAbsolutePath();
		String logicalPath = pathPattern.replace("../", "") + "/**";
		
		registry.addResourceHandler(logicalPath)
			.addResourceLocations("file:/" + absolutePath + "/");
	}
}
