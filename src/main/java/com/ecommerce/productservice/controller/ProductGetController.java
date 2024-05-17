package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.response.*;
import com.ecommerce.productservice.entity.ReviewRating;
import com.ecommerce.productservice.entity.warehousemanagement.Warehouse;
import com.ecommerce.productservice.service.declaration.ProductGetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/get")
public class ProductGetController {

    @Autowired
    ProductGetService productService;

    @Operation(summary = "To get Master Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Master Category based on parameters. Adding no parameter will return all from Db.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MasterCategoryDto.class)) }),
    })
    @GetMapping("/master-category")
    public ResponseEntity<List<MasterCategoryDto>> getMasterCategory(@RequestParam(required = false) String masterCategoryId,
                                                                     @RequestParam(required = false) String masterCategoryName){
        return new ResponseEntity<>(productService.getMasterCategory(masterCategoryId,masterCategoryName), HttpStatus.OK);
    }

    @Operation(summary = "To get Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Category based on parameters. Adding no parameter will return all from Db.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class)) }),
    })
    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getCategory(@RequestParam(required = false) String categoryId,
                                                         @RequestParam(required = false) String categoryName,
                                                         @RequestParam(required = false) String masterCategoryName){
        return new ResponseEntity<>(productService.getCategory(categoryName,categoryId,masterCategoryName), HttpStatus.OK);
    }

    @Operation(summary = "To get SubCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get SubCategory based on parameters. Adding no parameter will return all from Db.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubCategoryDto.class)) }),
    })
    @GetMapping("/sub-category")
    public ResponseEntity<List<SubCategoryDto>> getSubCategory(@RequestParam(required = false) String subCategoryId,
                                                               @RequestParam(required = false) String subCategoryName,
                                                               @RequestParam(required = false) String categoryName){
        return new ResponseEntity<>(productService.getSubCategory(subCategoryName,subCategoryId,categoryName), HttpStatus.OK);
    }

    @Operation(summary = "To get Brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all Brand.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
    })
    @GetMapping("/brand")
    public ResponseEntity<List<BrandDto>> getBrand(){
        return new ResponseEntity<>(productService.getBrand(), HttpStatus.OK);
    }

    @Operation(summary = "To get Product along with all related details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product with All related details",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)) }),
    })
    @GetMapping("/v1/product")
    public ResponseEntity<List<ProductResponse>> getProduct(
                                                 @RequestParam(required = false) String productId,
                                                 @RequestParam(required = false) String productName,
                                                 @RequestParam(required = false) String subCategoryName,
                                                 @RequestParam(required = false) String categoryName,
                                                 @RequestParam(required = false) String masterCategoryName,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) String gender)
    {

        return new ResponseEntity<>(productService.getProduct(productId,productName,subCategoryName,categoryName,
                                                     masterCategoryName,brand,gender), HttpStatus.OK);
    }

    @Operation(summary = "To get Product details for Product Listing Page with Parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product with required info only",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductListingResponse.class)) })
    })
    @GetMapping("/v1/product/listing")
    public ResponseEntity<Set<ProductListingResponse>> getProductListingV1(
                                                @RequestParam(required = false) String subCategoryName,
                                                @RequestParam(required = false) String categoryName,
                                                @RequestParam(required = false) String masterCategoryName,
                                                @RequestParam(required = false) String brand,
                                                @RequestParam(required = false) String gender)
    {
        return new ResponseEntity<>(productService.getProductListing(subCategoryName,categoryName,
                                                            masterCategoryName,brand,gender), HttpStatus.OK);
    }

    @Operation(summary = "To get Product details for Product Listing Page with any string")
    @Parameter(name = "pageNumber",description = "Number of the page to return")
    @Parameter(name = "numberOfItem",description = "Number of item of the page to return")
    @Parameter(name = "sortBy", description = "Sort results by 'popularity', 'HighToLow' & 'LowToHigh' price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product with required info only",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListingPageDetails.class)) })
    })
    @GetMapping("/v2/product/{searchString}")
    public ResponseEntity<ListingPageDetails> getProductListingV2(
            @PathVariable(value = "searchString") String searchString,
            @RequestParam(required = false,defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false,defaultValue = "10") Integer numberOfItem,
            @RequestParam(required = false,defaultValue = "popularity") String sortBy)
    {
        return new ResponseEntity<>(productService.getProductListingV2(searchString,sortBy,pageNumber,numberOfItem), HttpStatus.OK);
    }

    @Operation(summary = "To get Review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all Review for a product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewRating.class)) }),
    })
    @GetMapping("/review/{productid}")
    public ResponseEntity<List<ReviewRating>> getReview(@PathVariable(name = "productid") String productId){
        return new ResponseEntity<>(productService.getReview(productId), HttpStatus.OK);
    }

    @Operation(summary = "To get All Product Styles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Style",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StyleVariantDetailsDto.class)) }),
    })
    @GetMapping("/product/style")
    public ResponseEntity<List<StyleVariantDetailsDto>> getProductStyle(@RequestParam String productId,
                                                                      @RequestParam(required = false) String styleId,
                                                                      @RequestParam(required = false) String size,
                                                                      @RequestParam (required = false) String colour){
        return new ResponseEntity<>(productService.getStyleVariants(productId,styleId,size,colour), HttpStatus.OK);
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


    @Operation(summary = "To get All available sizes of a Product's SKU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Various sizes of product both in & out of stock",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SizeInfo.class))}),

    })
    @GetMapping("style/sizes")
    public ResponseEntity<List<SizeInfo>> getStyleSize(@RequestParam String styleId){
        return new ResponseEntity<>(productService.getSizes(styleId), HttpStatus.OK);
    }

    @Operation(summary = "To get all warehouses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all warehouse",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Warehouse.class)) }),
    })
    @GetMapping("/warehouse")
    public ResponseEntity<List<Warehouse>> getWarehouse(@RequestParam(required = false) Integer warehouseId){
        return new ResponseEntity<>(productService.getWarehouse(warehouseId), HttpStatus.OK);
    }
}