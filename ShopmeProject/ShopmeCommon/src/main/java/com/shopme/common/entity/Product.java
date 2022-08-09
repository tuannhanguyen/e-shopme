package com.shopme.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, length = 256, nullable = false)
	private String name;
	
	@Column(unique = true, length = 256, nullable = false)
	private String alias;
	
	@Column(name = "short_description", length = 4096, nullable = false)
	private String shortDescription;
	
	@Column(name = "full_description", length = 4096, nullable = false)
	private String fullDescription;

	@Column(name = "created_time")
	private Date createdDate;
	
	@Column(name = "updated_time")
	private Date updatedDate;

	private boolean enabled;
	
	@Column(name = "in_stock")
	private boolean inStock;

	private float cost;

	private float price;
	
	@Column(name = "discount_percent")
	private float discountPercent;

	private float length;
	private float width;
	private float height;
	private float weight;
	
	@Column(name = "main_image", nullable = false)
	private String mainImage;

	@ManyToOne
	@JoinColumn( name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn( name = "brand_id")
	private Brand brand;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductImage> images = new HashSet<>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductDetails> details = new ArrayList<>();
	
	

	public Product() {
		
	}

	public Product(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<ProductDetails> getDetails() {
		return details;
	}

	public void setDetails(List<ProductDetails> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String main_image) {
		this.mainImage = main_image;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}
	
	public void addExtraImage(String image) {
		this.images.add(new ProductImage(image, this));
	}
	
	public void addDetails(String name, String value) {
		this.details.add(new ProductDetails(name, value, this));
	}
	
	public void addDetails(Integer id, String name, String value) {
		this.details.add(new ProductDetails(id, name, value, this));
	}
	
	@Transient
	public String getMainImagePath() {
		if (id == null || mainImage == null) {
			return "/images/image-thumbnail.png";
		}
		return "/product-images/" + this.id + "/" + this.mainImage;
	}

	public boolean containsImageName(String imageName) {
		Iterator<ProductImage> iterator = images.iterator();
		
		while (iterator.hasNext()) {
			ProductImage image = iterator.next();
			if(image.getName().equals(imageName)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Transient
	public String getShortName() {
		if(this.name.length() > 70) {
			return name.substring(0, 70).concat("..");
		}
		return name;
	}
	
	@Transient
	public float getDiscountPrice() {
		if(this.discountPercent > 0) {
			return price * ((100 - this.discountPercent) /100);
		}
		
		return this.price;
	}
}
