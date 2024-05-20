package com.ecommerce.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingPageDetails{
        List<ProductListingResponse> responseList;
        List<BreadCrumb> breadCrumbs;
        int totalPages;
        long totalElements;
        boolean hasNextPage;
}
