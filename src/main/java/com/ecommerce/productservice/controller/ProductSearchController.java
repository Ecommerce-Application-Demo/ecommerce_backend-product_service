package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductFilters;
import com.ecommerce.productservice.dto.response.ColourInfo;
import com.ecommerce.productservice.dto.response.ListingPageDetails;
import com.ecommerce.productservice.dto.response.SizeInfo;
import com.ecommerce.productservice.service.declaration.ProductSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/get/search")
@Tag(name = "Product Search Controller",description = "REST APIs for getting search results & filters")
public class ProductSearchController {
    @Autowired
    ProductSearchService productService;


    @Operation(summary = "To get Product details for Product Listing Page with Parameters")
    @Parameter(name = "pageNumber",description = "Number of the page to return")
    @Parameter(name = "numberOfItem",description = "Number of item of the page to return")
    @Parameter(name = "sortBy", description = "Sort results by 'popularity', 'HighToLow' & 'LowToHigh' price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Listing page details with applied Filters & Pagination",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingPageDetails.class)) })
    })
    @GetMapping("/product/listing")
    public ResponseEntity<ListingPageDetails> getProductListingV1(
            @RequestParam(required = false) String masterCategoryName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String subCategoryName,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String colour,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Integer discountPercentage,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false,defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false,defaultValue = "10") Integer numberOfItem,
            @RequestParam(required = false,defaultValue = "popularity") String sortBy)
    {
        return new ResponseEntity<>(productService.getProductListing(masterCategoryName,categoryName,subCategoryName,brand,
                gender,colour,size,discountPercentage,minPrice,maxPrice,sortBy,pageNumber,numberOfItem), HttpStatus.OK);
    }

    @Operation(summary = "To get Product details for Product Listing Page with any Search String")
    @Parameter(name = "pageNumber",description = "Number of the page to return")
    @Parameter(name = "numberOfItem",description = "Number of item of the page to return")
    @Parameter(name = "sortBy", description = "Sort results by 'popularity', 'HighToLow' & 'LowToHigh' price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Listing Page details for Specified Search String with Pagination."+
                                                                " Example : 'Men red tshirts under 499 '",

                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingPageDetails.class)) })
    })
    @GetMapping("/product/listing/{searchString}")
    public ResponseEntity<ListingPageDetails> getProductListingV2(
            @PathVariable(value = "searchString") String searchString,
            @RequestParam(required = false,defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false,defaultValue = "10") Integer numberOfItem,
            @RequestParam(required = false,defaultValue = "popularity") String sortBy)
    {
        return new ResponseEntity<>(productService.getProductListingV2(searchString,sortBy,pageNumber,numberOfItem), HttpStatus.OK);
    }


    @Operation(summary = "Get all applicable filter list for specified Search String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product applicable filters",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductFilters.class)) })
    })
    @GetMapping("/product/{searchString}/filters")
    public ResponseEntity<ProductFilters> getProductFilter(@PathVariable(value = "searchString") String searchString){
        return new ResponseEntity<>(productService.getProductFilters(searchString), HttpStatus.OK);
    }

    @Operation(summary = "To get All Colour Variants of a Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Various colour of product both in & out of stock",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ColourInfo.class)) }),
    })
    @GetMapping("/product/colours")
    public ResponseEntity<Set<ColourInfo>> getaProductColours(@RequestParam String productId){
        return new ResponseEntity<>(productService.getColours(productId), HttpStatus.OK);
    }


    @Operation(summary = "To get All available sizes of a Product's Styles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Various sizes of product both in & out of stock",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SizeInfo.class))}),
    })
    @GetMapping("style/sizes")
    public ResponseEntity<List<SizeInfo>> getStyleSize(@RequestParam String styleId){
        return new ResponseEntity<>(productService.getSizes(styleId), HttpStatus.OK);
    }
}
