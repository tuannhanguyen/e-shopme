package com.shopme.admin.product;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.brand.BrandService;
import com.shopme.admin.category.CategoryService;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import com.shopme.common.exception.ProductNotFoundException;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/products")
	public String listAll(Model model) {
		List<Product> products = productService.listAll();

		model.addAttribute("products", products);

		return listByPage(1, "name", "asc", null, 0, model);
	}
	
	@GetMapping("products/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") Integer pageNum, 
			@Param("sortField") String sortField, 
			@Param("sortDir") String sortDir, 
			@Param("keyword") String keyword,
			@Param("categoryId") Integer categoryId,
			Model model) {
		
		Page<Product> page = productService.listByPage(pageNum, sortField, sortDir, keyword, categoryId);
		
		List<Category> listCategories = categoryService.listCategoriesUsedInForm();
		int totalPages = page.getTotalPages(); 
		long totalItems = page.getTotalElements();
		long startCount = (pageNum - 1 ) * ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		
		if(endCount > totalItems) {
			endCount = totalItems;
		}
		List<Product> products = page.getContent();
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		
		model.addAttribute("products", products);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("moduleURL", "/products");
		
		return "products/products";
	}

	@GetMapping("/products/new")
	public String newProduct(Model model) {
		List<Brand> listBrands = brandService.listFirstPage();

		Product product = new Product();
		product.setEnabled(true);
		product.setInStock(true);

		model.addAttribute("product", product);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("titlePage", "Create New Product");

		return "products/product_form";
	}

	// MultipartFile la doi tuong file duoc upload len
	@PostMapping("/products/save")
	public String save(Product product, RedirectAttributes redirectAttributes,
			@RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,
			@RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
			@RequestParam(name = "detailIDs", required = false) String[] detailIDs,
			@RequestParam(name = "detailNames", required = false) String[] detailNames,
			@RequestParam(name = "detailValues", required = false) String[] detailValues,
			@RequestParam(name = "imageIDs", required = false) Integer[] imageIDs,
			@RequestParam(name = "imageNames", required = false) String[] imageNames,
			@AuthenticationPrincipal ShopmeUserDetails loggedUser) throws IOException {
		
		if(!loggedUser.hasRole("Admin") && !loggedUser.hasRole("Editor")) {
			if( loggedUser.hasRole("Salesperson")) {
				productService.saveProductPrice(product);
				redirectAttributes.addFlashAttribute("message", "The product has been saved successfully ");
				return "redirect:/products";
			}
		}

		ProductSaveHelper.setMainImage(mainImageMultipart, product);

		ProductSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);

		ProductSaveHelper.setNewExtraImageNames(extraImageMultiparts, product);

		ProductSaveHelper.setProductsDetails(detailIDs, detailNames, detailValues, product);

		Product savedProduct = productService.save(product);

		ProductSaveHelper.saveUploadedImage(mainImageMultipart, extraImageMultiparts, savedProduct);

		ProductSaveHelper.deleteExtraImagesWereRemovedOnForm(product);

		redirectAttributes.addFlashAttribute("message", "The product has been saved successfully ");
		return "redirect:/products";
	}

	
	@GetMapping("/products/{id}/enabled/{enabled}")
	public String updateEnabledStatus(@PathVariable("id") Integer id, @PathVariable("enabled") boolean enabled,
			RedirectAttributes redirectAttributes) {
		try {
			productService.updateEnabledStatus(id, enabled);
			String message = enabled ? "enabled" : "disabled";
			redirectAttributes.addFlashAttribute("message", "The product ID " + id + " has been " + message);

		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}

		return "redirect:/products";
	}

	@GetMapping("/products/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			productService.delete(id);
			String productExtraImagesDir = "../product-images/" + id + "/extras";
			String productImagesDir = "../product-images/" + id;

			FileUploadUtil.removeDir(productExtraImagesDir);
			FileUploadUtil.removeDir(productImagesDir);

			redirectAttributes.addFlashAttribute("message", "The product ID " + id + " has been deleted ");

		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}

		return "redirect:/products";
	}

	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal ShopmeUserDetails logged) {
		try {
			Product product = productService.get(id);
			List<Brand> listBrands = brandService.listFirstPage();
			Integer numberOfExistingExtraImage = product.getImages().size();
			Integer listSize = product.getDetails().size();
			
			boolean isReadOnlyForSalesperson = false;
			
			if (!logged.hasRole("Admin") && !logged.hasRole("Editor")) {
				if(logged.hasRole("Salesperson")) {
					isReadOnlyForSalesperson = true;
				}
			}
			
			model.addAttribute("isReadOnlyForSalesperson", isReadOnlyForSalesperson);
			model.addAttribute("product", product);
			model.addAttribute("titlePage", "Edit product (ID: " + id + " )");
			model.addAttribute("listBrands", listBrands);
			model.addAttribute("numberOfExistingExtraImage", numberOfExistingExtraImage);
			model.addAttribute("listSize", listSize);

			return "products/product_form";

		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/products";
		}
	}
	
	@GetMapping("/products/details/{id}")
	public String viewProductDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			Product product = productService.get(id);
			model.addAttribute("product", product);

			return "products/product_detail_modal";

		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/products";
		}
	}
	
}

