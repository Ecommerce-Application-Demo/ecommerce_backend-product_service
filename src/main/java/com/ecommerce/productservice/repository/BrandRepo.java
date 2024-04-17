package com.ecommerce.productservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.productservice.entity.Brand;

public interface BrandRepo extends CrudRepository<Brand, String> {

}
