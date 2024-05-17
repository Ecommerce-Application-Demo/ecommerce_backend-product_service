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
public class SubCategory {

	private String SubCategoryId;
	@Id
	private String SubCategoryName;
	@Column(length = 500)
	private String SubCategoryDescription;
	private String SubCategoryDefaultImage;
	private String scBreadcrumbUrl;
	@ManyToOne
	@JoinColumn(name = "sub_category_category")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "sub_category_master_category")
	private MasterCategory masterCategory;
	@OneToMany(mappedBy = "SubCategory",cascade = CascadeType.ALL)
	private List<Product> product;
}
