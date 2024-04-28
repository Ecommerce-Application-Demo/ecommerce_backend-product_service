package com.ecommerce.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sku {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String skuId;
	private String skuName;
	private String colour;
	private BigDecimal mrp;
	private BigDecimal discountPercentage;
	private BigDecimal finalPrice;
	@ElementCollection
	@CollectionTable(name = "size_variants")
	private List<SizeVariantDetails> sizeVariantDetails = new ArrayList<>();
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private Images images;
	@ManyToOne
	@JoinColumn(name = "sku_product")
	@JsonIgnore
	private Product product;
}
