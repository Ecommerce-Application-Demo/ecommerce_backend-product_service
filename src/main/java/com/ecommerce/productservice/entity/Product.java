package com.ecommerce.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy =GenerationType.UUID )
	private String productId;
	private String productName;
	@Column(length = 1000)
	private String productDescription;
	private Float productAvgRating;
	private Long reviewCount;
	private String gender;
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private String material;
	private LocalDateTime createdTimestamp;
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
