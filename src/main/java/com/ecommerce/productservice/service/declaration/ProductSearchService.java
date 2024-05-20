package com.ecommerce.productservice.service.declaration;

import com.ecommerce.productservice.dto.ProductFilters;
import com.ecommerce.productservice.dto.response.ColourInfo;
import com.ecommerce.productservice.dto.response.ListingPageDetails;
import com.ecommerce.productservice.dto.response.SizeInfo;

import java.util.List;
import java.util.Set;

public interface ProductSearchService {

    List<SizeInfo> getSizes(String styleId);

    Set<ColourInfo> getColours(String productId);

    ListingPageDetails getProductListing(String masterCategoryName, String categoryName, String subCategoryName,String brand,
                                         String gender, String colour, String size, Integer discountPercentage,
                                         Integer minPrice, Integer maxPrice, String sortBy,Integer pageNumber, Integer pageSize);

    ListingPageDetails getProductListingV2(String searchString, String sortBy, Integer page, Integer pageSize);

    ProductFilters getProductFilters(String searchString);
}
