package com.shopme.admin.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;

public class ProductSaveHelper {
	
	static void deleteExtraImagesWereRemovedOnForm(Product product) {
		String extraImageDir = "../product-images/" + product.getId() + "/extras";
		Path dirPath = Paths.get(extraImageDir);

		try {
			Files.list(dirPath).forEach(file -> {
				String fileName = file.toFile().getName();

				if (!product.containsImageName(fileName)) {
					try {
						Files.delete(file);
						System.out.println("Deleted extra image!");
					} catch (IOException e) {
						System.out.println("Could not delete " + fileName);
					}
				}
			});
		} catch (IOException e) {
			System.out.println("Could not list directory " + extraImageDir);
		}
	}

	static void setExistingExtraImageNames(Integer[] imageIDs, String[] imageNames, Product product) {
		if (imageIDs == null || imageIDs.length == 0)
			return;

		Set<ProductImage> images = new HashSet<>();

		for (int count = 0; count < imageIDs.length; count++) {
			Integer id = imageIDs[count];
			String name = imageNames[count];
			images.add(new ProductImage(id, name, product));
		}

		product.setImages(images);
	}

	static void setNewExtraImageNames(MultipartFile[] extraImageMultiparts, Product product) {
		if (extraImageMultiparts.length > 0) {
			System.out.println("length: " + extraImageMultiparts.length);
			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (!multipartFile.isEmpty()) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

					if (!product.containsImageName(fileName)) {
						product.addExtraImage(fileName);
					}
				}
			}
		}
	}

	static void setProductsDetails(String[] detailIDs, String[] detailsNames, String[] detailsValues,
			Product product) {
		if (detailsNames == null || detailsNames.length == 0 || detailsValues == null || detailsValues.length == 0)
			return;
		System.out.println("detailsNames.length: " + detailsNames.length);
		for (int count = 0; count < detailsNames.length; count++) {
			String name = detailsNames[count];
			String value = detailsValues[count];
			Integer id = Integer.parseInt(detailIDs[count]);
			
			if (id != 0) { //update
				product.addDetails(id, name, value);
			} else {
				if (!name.isEmpty() && !value.isEmpty()) { //new (id=0)
					product.addDetails(name, value);
				}
			}
		}
	}
	
	static void saveUploadedImage(MultipartFile mainImageMultipart, MultipartFile[] extraImageMultiparts,
			Product savedProduct) throws IOException {

		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			String uploadDir = "../product-images/" + savedProduct.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
		}

		if(extraImageMultiparts.length > 0) { 
			
			String uploadDir = "../product-images/" + savedProduct.getId() + "/extras";
		
			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (!multipartFile.isEmpty()) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
					}
				}
			}
		}

	static void setMainImage(MultipartFile mainImageMultipart, Product product) {
		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			product.setMainImage(fileName);
		}
	}
}
