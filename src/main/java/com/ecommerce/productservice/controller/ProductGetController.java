package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.entity.ReviewRating;
import com.ecommerce.productservice.service.declaration.ProductGetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/get")
public class ProductGetController {

    @Autowired
    ProductGetService productService;
    @Autowired
    Environment environment;

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

    @Operation(summary = "To get Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)) }),
    })
    @GetMapping("/product")
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

    @Operation(summary = "To get Review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all Review for a product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewRating.class)) }),
    })
    @GetMapping("/review/{productid}")
    public ResponseEntity<List<ReviewRating>> getReview(@PathVariable(name = "productid") UUID productId){
        return new ResponseEntity<>(productService.getReview(productId), HttpStatus.OK);
    }

    @Operation(summary = "To get All Product SKUs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product SKU",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SkuDto.class)) }),
    })
    @GetMapping("/product/sku")
    public ResponseEntity<List<SkuDto>> getProductSku(@RequestParam String productId,
                                                   @RequestParam(required = false) String skuId,
                                                   @RequestParam(required = false) String size,
                                                   @RequestParam (required = false) String colour){
        return new ResponseEntity<>(productService.getSku(productId,skuId,size,colour), HttpStatus.OK);
    }


    @Operation(summary = "To get All Colour Variants of a Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Various colour of product both in & out of stock",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvailableColours.class)) }),
    })
    @GetMapping("/product/colours")
    public ResponseEntity<AvailableColours> getSkuColours(@RequestParam String productId){
        return new ResponseEntity<>(productService.getColours(productId), HttpStatus.OK);
    }


    @Operation(summary = "To get All available sizes of a Product's SKU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Various sizes of product both in & out of stock",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(type = "Integer", additionalProperties = Schema.AdditionalPropertiesValue.USE_ADDITIONAL_PROPERTIES_ANNOTATION,
                            example = "{ L : 30 }")) }),

    })
    @GetMapping("sku/sizes")
    public ResponseEntity<Map<String,Integer>> getSkuSize(@RequestParam String skuId){
        return new ResponseEntity<>(productService.getSizes(skuId), HttpStatus.OK);
    }
}