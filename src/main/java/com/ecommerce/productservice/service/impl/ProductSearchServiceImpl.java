package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.dto.ProductFilters;
import com.ecommerce.productservice.dto.SubCategoryDto;
import com.ecommerce.productservice.dto.response.*;
import com.ecommerce.productservice.entity.Brand;
import com.ecommerce.productservice.entity.MasterCategory;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductStyleVariant;
import com.ecommerce.productservice.repository.ProductRepo;
import com.ecommerce.productservice.repository.StyleVariantRepo;
import com.ecommerce.productservice.service.declaration.ProductGetService;
import com.ecommerce.productservice.service.declaration.ProductSearchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    StyleVariantRepo styleVariantRepo;
    @Autowired
    ProductGetService productGetService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ListingPageDetails getProductListing( String masterCategoryName, String categoryName, String subCategoryName,String brand,
                                                           String gender, String colour, String size, Integer discountPercentage,
                                                          Integer minPrice, Integer maxPrice, String sortBy,Integer page, Integer pageSize ) {

        List<ProductListingResponse> productListingResponse = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<ProductStyleVariant> styleVariants = null;

        if (sortBy.equalsIgnoreCase("HighToLow")) {
            styleVariants = styleVariantRepo.getFilteredProduct(masterCategoryName,categoryName,subCategoryName,brand,gender,
                                                                colour,size,discountPercentage,minPrice,maxPrice,
                                                                "psv.final_price DESC", pageRequest);
        }
        if (sortBy.equalsIgnoreCase("LowToHigh")) {
            styleVariants = styleVariantRepo.getFilteredProduct(masterCategoryName,categoryName,subCategoryName,brand,gender,
                                                                colour,size,discountPercentage,minPrice,maxPrice,
                                                                "psv.final_price ASC", pageRequest);
        }
        if (sortBy.equalsIgnoreCase("popularity")) {
            styleVariants = styleVariantRepo.getFilteredProduct(masterCategoryName,categoryName,subCategoryName,brand,gender,
                                                                colour,size,discountPercentage,minPrice,maxPrice,
                                                                "p.product_avg_rating DESC", pageRequest);
        }
        return getListingPageDetails(productListingResponse, styleVariants);
    }

    @Override
    public ListingPageDetails getProductListingV2(String searchString, String sortBy, Integer page, Integer pageSize) {
        List<ProductListingResponse> productListingResponse = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<ProductStyleVariant> styleVariants = null;
        String[] searchString2;
        Integer price = null;
        searchString=searchString.replaceAll("-"," ");
        if (Pattern.compile("under (\\d+)").matcher(searchString).find()) {
            searchString2 = searchString.split("under ");
            searchString = searchString2[0];
            price = Integer.parseInt(searchString2[1]);
        }
        if (sortBy.equalsIgnoreCase("HighToLow")) {
            styleVariants = styleVariantRepo.findProductByField(searchString, price, "psv.final_price DESC", pageRequest);
        }
        if (sortBy.equalsIgnoreCase("LowToHigh")) {
            styleVariants = styleVariantRepo.findProductByField(searchString, price, "psv.final_price ASC", pageRequest);
        }
        if (sortBy.equalsIgnoreCase("popularity")) {
            styleVariants = styleVariantRepo.findProductByField(searchString, price, "p.product_avg_rating DESC", pageRequest);
        }
        return getListingPageDetails(productListingResponse, styleVariants);
    }

    private ListingPageDetails getListingPageDetails(List<ProductListingResponse> productListingResponse, Page<ProductStyleVariant> styleVariants) {
        styleVariants.forEach(styleVariant -> {
            AtomicBoolean flag = new AtomicBoolean(false);
            getSizes(styleVariant.getStyleId()).forEach(size -> {
                if (size.quantity() != null && size.quantity() > 0)
                    flag.set(true);
            });
            ProductListingResponse res = modelMapper.map(styleVariant, ProductListingResponse.class);
            Product product = productRepo.findById(res.getProductId()).get();
            res.setBrand(product.getBrand());
            res.setProductAvgRating(product.getProductAvgRating().toString());
            res.setReviewCount(product.getReviewCount().toString());
            res.setDefaultImage(styleVariant.getImages().getImage1());
            res.setInStock(flag.get());
            productListingResponse.add(res);

        });
        return new ListingPageDetails(productListingResponse, styleVariants.getTotalPages(), styleVariants.getTotalElements(),
                                        styleVariants.hasNext());
    }

    @Override
    public ProductFilters getProductFilters(String searchString) {
        List<ProductStyleVariant> styleVariants;
        Set<MasterCategory> masterCategories = new HashSet<>();
        Set<CategoryDto> categories = new HashSet<>();
        Set<SubCategoryDto> subCategories =new HashSet<>();
        Set<Brand> brands=new HashSet<>();
        Set<ColourHexCode> colours=new HashSet<>();
        Set<String> sizes=new HashSet<>();
        Set<BigDecimal> discountPercentages=new HashSet<>();
        AtomicInteger maxPrice = new AtomicInteger(0);
        AtomicInteger minPrice= new AtomicInteger(1000000000);

        String[] searchString2;
        Integer price = null;

        searchString=searchString.replaceAll("-"," ");
        if (Pattern.compile("under (\\d+)").matcher(searchString).find()) {
            searchString2 = searchString.split("under ");
            searchString = searchString2[0];
            price = Integer.parseInt(searchString2[1]);
        }
        styleVariants = styleVariantRepo.findProductFilters(searchString, price);
        styleVariants.forEach(psv -> {
            Product product = psv.getProduct();
            masterCategories.add(product.getMasterCategory());
            categories.add( modelMapper.map(product.getCategory(), CategoryDto.class));
            if(product.getSubCategory()!=null )
                subCategories.add( modelMapper.map(product.getSubCategory(), SubCategoryDto.class));
            brands.add(product.getBrand());
            colours.add(new ColourHexCode(psv.getColour(),psv.getColourHexCode()));
            psv.getSizeDetails().forEach(sizeDetail -> {
                if (sizeDetail.getSize() != null)
                    sizes.add(sizeDetail.getSize());
            });
            discountPercentages.add(psv.getDiscountPercentage());
            if(psv.getFinalPrice().intValue()>maxPrice.intValue())
                maxPrice.set(psv.getFinalPrice().intValue());
            if (psv.getFinalPrice().intValue()<minPrice.intValue())
                minPrice.set(psv.getFinalPrice().intValue());
        });

        return new ProductFilters(masterCategories,categories,subCategories,brands,colours,sizes,
                        discountPercentages,BigDecimal.valueOf(maxPrice.intValue()),BigDecimal.valueOf(minPrice.intValue()));
    }

    @Override
    public List<SizeInfo> getSizes(String styleId) {
        ProductStyleVariant productStyleVariant = styleVariantRepo.findById(styleId).orElseGet(ProductStyleVariant::new);
        List<SizeInfo> sizes = new ArrayList<>();
        if (!productStyleVariant.getSizeDetails().isEmpty()) {
            productStyleVariant.getSizeDetails().forEach(sizeDetails -> {
                sizes.add(new SizeInfo(sizeDetails.getSizeId(), sizeDetails.getSize(), sizeDetails.getQuantity()));
            });
        }
        return sizes;
    }

    @Override
    public Set<ColourInfo> getColours(String productId) {
        List<ProductStyleVariant> productStyleVariantList = styleVariantRepo.findStyle(productId, null, null, null);
        Set<ColourInfo> colourInfos = new HashSet<>();
        if (!productStyleVariantList.isEmpty()) {
            productStyleVariantList.forEach(psv -> {
                AtomicBoolean flag = new AtomicBoolean(false);
                getSizes(psv.getStyleId()).forEach(size -> {
                    if (size.quantity() != null && size.quantity() > 0)
                        flag.set(true);
                });
                if (flag.get())
                    colourInfos.add(new ColourInfo(psv.getStyleId(), psv.getColour(), psv.getImages().getImage1(), true));
                else
                    colourInfos.add(new ColourInfo(psv.getStyleId(), psv.getColour(), psv.getImages().getImage1(), false));
            });
        }
        return colourInfos;
    }
}
