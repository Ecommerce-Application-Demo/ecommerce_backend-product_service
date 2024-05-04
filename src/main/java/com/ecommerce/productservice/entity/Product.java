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
public class Product {

	@Id
	@GeneratedValue(strategy =GenerationType.UUID )
	private UUID productId;
	private String productName;
	@Column(length = 1000)
	private String productDescription;
	private String productAvgRating;
	private String reviewCount;
	private String gender;
	@Column(length = 1000)
	private String material;
	@ManyToOne
	@JoinColumn(name = "product_master_category")
	private MasterCategory masterCategory;
	@ManyToOne
	@JoinColumn(name = "product_category")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "product_sub_category")
	private SubCategory SubCategory;
	@ManyToOne
	@JoinColumn(name = "product_brand")
	private Brand brand;
	@OneToMany(mappedBy = "product")
	private List<ProductStyleVariant> productStyleVariant;
}
