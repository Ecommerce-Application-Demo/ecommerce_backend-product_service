package com.ecommerce.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

	private String categoryId;
	@Id
	private String categoryName;
	@Column(length = 500)
	private String categoryDescription;
	private String categoryDefaultImage;
	private String cBreadcrumbUrl;
	@ManyToOne
	@JoinColumn(name = "category_master_category")
	private MasterCategory masterCategory;
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<SubCategory> SubCategory;
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<Product> product;
}
