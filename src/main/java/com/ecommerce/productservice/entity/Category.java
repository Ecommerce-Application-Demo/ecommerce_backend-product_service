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
public class Category {

	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID categoryId;
	@Id
	private String categoryName;
	@Column(length = 500)
	private String categoryDescription;
	private String categoryDefaultImage;
	private String breadcrumbUrl;
	@ManyToOne
	@JoinColumn(name = "category_master_category")
	private MasterCategory masterCategory;
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<SubCategory> SubCategory;
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<Product> product;
}
