package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.request.*;
import com.ecommerce.productservice.entity.ReviewRating;
import com.ecommerce.productservice.entity.warehousemanagement.Inventory;
import com.ecommerce.productservice.entity.warehousemanagement.Warehouse;
import com.ecommerce.productservice.exception.ErrorResponse;
import com.ecommerce.productservice.exception.ProductException;
import com.ecommerce.productservice.service.declaration.ProductAddService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/add")
@Validated
@SecurityRequirement(name = "apiKey")
@Tag(name = "4. Product Add Controller",description = "REST APIs for Adding Products,Categories,inventory,warehouses into database. Protected with API Key-Secret")
public class ProductAddController {

    @Autowired
    ProductAddService productService;

    @Operation(summary = "To add Master Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Master Category Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MasterCategoryDto.class)) }),
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
             })
    @PostMapping("/master-category")
    public ResponseEntity<MasterCategoryDto> addMasterCategory(@RequestBody MasterCategoryDto masterCategoryRequest){
        return new ResponseEntity<>(productService.addMasterCategory(masterCategoryRequest), HttpStatus.OK);
    }

    @Operation(summary = "To add Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class)) }),
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
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
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/sub-category")
    public ResponseEntity<SubCategoryDto> addSubCategory(@RequestBody SubCategoryRequest subCategoryRequest){
        return new ResponseEntity<>(productService.addSubCategory(subCategoryRequest), HttpStatus.OK);
    }

    @Operation(summary = "To add Brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand` Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDto.class)) }),
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
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
            @ApiResponse(responseCode = "400", description = "'Bad Request'. Input Validation(s) failed.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductRequest productRequest){
        return new ResponseEntity<>(productService.addProduct(productRequest), HttpStatus.OK);
    }

    @Operation(summary = "To add Review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewRating.class)) }),
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/review")
    public ResponseEntity<ReviewRating> addReview(@RequestBody ReviewRating reviewRating) throws ProductException {
        return new ResponseEntity<>(productService.addReview(reviewRating), HttpStatus.OK);
    }

    @Operation(summary = "To add Product Styles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Style Added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StyleVariantDetailsDto.class)) }),
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/product/style")
    public ResponseEntity<StyleVariantDetailsDto> addProductStyle(@RequestBody StyleVariantRequest request){
        return new ResponseEntity<>(productService.addStyleVariant(request), HttpStatus.OK);
    }

    @Operation(summary = "To add Warehouses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Warehouse.class)) }),
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
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
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))) })
    })
    @PostMapping("/product/inventory")
    public ResponseEntity<List<Inventory>> addInventory(@RequestBody @Valid List<@Valid InventoryReq> inventory){
        return new ResponseEntity<>(productService.addInventory(inventory), HttpStatus.OK);
    }

    @Operation(summary = "To Edit Product Style Inventory Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Style Inventory edited",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inventory.class)) }),
            @ApiResponse(responseCode = "401", description = "API Secret is Invalid or API Secret is Invalid",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))) })
    })
    @PutMapping("/product/inventory")
    public ResponseEntity<Inventory> editInventory(@RequestBody  Inventory inventory){
        return new ResponseEntity<>(productService.editInventory(inventory), HttpStatus.OK);
    }
}
