package com.ecommerce.productservice.dto.response;

import java.util.List;

public record ListingPageDetails(List<ProductListingResponse> responseList, int totalPages, long totalElements,
                                 boolean hasNextPage) {
}
