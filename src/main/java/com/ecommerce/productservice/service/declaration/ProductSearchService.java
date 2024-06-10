package com.ecommerce.productservice.service.declaration;

import com.ecommerce.productservice.dto.ProductFilters;
import com.ecommerce.productservice.dto.response.ColourInfo;
import com.ecommerce.productservice.dto.response.ListingPageDetails;
import com.ecommerce.productservice.dto.response.SingleProductResponse;
import com.ecommerce.productservice.dto.response.SizeInfo;

import java.util.List;
import java.util.Set;

public interface ProductSearchService {

    List<SizeInfo> getSizes(String styleId);

    SingleProductResponse getSingleProductDetails(String styleId, String styleName);

    ListingPageDetails getProductListingParameters(String masterCategoryName, String categoryName, String subCategoryName, String brand,
                                                   String gender, String colour, String size, Integer discountPercentage,
                                                   Integer minPrice, Integer maxPrice, String sortBy, Integer pageNumber, Integer pageSize);

    ListingPageDetails getProductListingSearchString(String searchString, String sortBy, Integer page, Integer pageSize);

    ProductFilters getProductFilters(String searchString);

    ProductFilters getProductParameterFilter(String masterCategoryName, String categoryName, String subCategoryName, String brand, String gender, String colour, Integer discountPercentage);

    Set<ColourInfo> getColours(String productId, String styleId);
}
