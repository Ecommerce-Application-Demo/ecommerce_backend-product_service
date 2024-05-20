package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.request.CategoryRequest;
import com.ecommerce.productservice.dto.request.ProductRequest;
import com.ecommerce.productservice.dto.request.StyleVariantRequest;
import com.ecommerce.productservice.dto.request.SubCategoryRequest;
import com.ecommerce.productservice.entity.ProductStyleVariant;
import com.ecommerce.productservice.entity.ReviewRating;
import com.ecommerce.productservice.entity.warehousemanagement.Inventory;
import com.ecommerce.productservice.entity.warehousemanagement.Warehouse;
import com.ecommerce.productservice.service.declaration.ProductAddService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/add")
@Validated
@Tag(name = "Product Add Controller",description = "REST APIs for Adding Products,Categories,inventory,warehouses into database. Protected with API Key-Secret")
public class ProductAddController {

    @Autowired
    ProductAddService productService;
    @Autowired
    Environment environment;

    @Operation(summary = "To add Master Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Master Category Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MasterCategoryDto.class)) }),
             })
    @PostMapping("/master-category")
    public ResponseEntity<MasterCategoryDto> addMasterCategory(@RequestBody MasterCategoryDto masterCategoryDto){
        return new ResponseEntity<>(productService.addMasterCategory(masterCategoryDto), HttpStatus.OK);
    }

    @Operation(summary = "To add Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class)) }),
    })
    @PostMapping("/category")

    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryRequest categoryRequest){
        return new ResponseEntity<>(productService.addCategory(categoryRequest), HttpStatus.OK);
    }

    @Operation(summary = "To add SubCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubCategory Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubCategoryDto.class)) }),
    })
    @PostMapping("/sub-category")
    public ResponseEntity<SubCategoryDto> addSubCategory(@RequestBody SubCategoryRequest subCategoryDto){
        return new ResponseEntity<>(productService.addSubCategory(subCategoryDto), HttpStatus.OK);
    }

    @Operation(summary = "To add Brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand` Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDto.class)) }),
    })
    @PostMapping("/brand")
    public ResponseEntity<BrandDto> addBrand(@RequestBody BrandDto brandDto){
        return new ResponseEntity<>(productService.addBrand(brandDto), HttpStatus.OK);
    }

    @Operation(summary = "To add Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
    })
    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductRequest productDto){
        return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.OK);
    }

    @Operation(summary = "To add Review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewRating.class)) }),
    })
    @PostMapping("/review")
    public ResponseEntity<ReviewRating> addReview(@RequestBody ReviewRating reviewRating){
        return new ResponseEntity<>(productService.addReview(reviewRating), HttpStatus.OK);
    }

    @Operation(summary = "To add Product Styles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Style Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductStyleVariant.class)) }),
    })
    @PostMapping("/product/style")
    public ResponseEntity<ProductStyleVariant> addProductStyle(@RequestBody StyleVariantRequest request){
        return new ResponseEntity<>(productService.addStyleVariant(request), HttpStatus.OK);
    }

    @Operation(summary = "To add Warehouses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Warehouse.class)) }),
    })
    @PostMapping("/warehouse")
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse){
        return new ResponseEntity<>(productService.addWarehouse(warehouse), HttpStatus.OK);
    }

    @Operation(summary = "To add Product Style Inventory Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Style Inventory",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inventory.class)) }),
    })
    @PostMapping("/product/inventory")
    public ResponseEntity<List<Inventory>> addInventory(@RequestBody List<Inventory> inventory){
        return new ResponseEntity<>(productService.addInventory(inventory), HttpStatus.OK);
    }

    @GetMapping("/index")
    public String index(){
        return "From ProductAddController";
    }
}
