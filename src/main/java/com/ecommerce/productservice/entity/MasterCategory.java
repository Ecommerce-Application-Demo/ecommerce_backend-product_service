package com.ecommerce.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MasterCategory {

	private String masterCategoryId;
	@Id
	private String masterCategoryName;
	@Column(length = 500)
	private String masterCategoryDescription;
	private String masterCategoryDefaultImage;
	private String mcBreadcrumbUrl;
	@OneToMany(mappedBy = "masterCategory",cascade = CascadeType.ALL)
	private List<Category> category;
	@OneToMany(mappedBy = "masterCategory",cascade = CascadeType.ALL)
	private List<SubCategory> SubCategory;
	@OneToMany(mappedBy = "masterCategory",cascade = CascadeType.ALL)
	private List<Product> product;
	
}
