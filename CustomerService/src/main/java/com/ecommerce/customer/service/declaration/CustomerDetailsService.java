package com.ecommerce.customer.service.declaration;

import java.util.List;

import com.ecommerce.customer.dto.AddressDto;
import com.ecommerce.customer.dto.CustomerDto;
import com.ecommerce.customer.dto.StringInputDto;
import com.ecommerce.customer.exception.CustomerException;

public interface CustomerDetailsService {

	CustomerDto customerDetails(String string);
	Boolean passwordVerify(StringInputDto stringInputDto) throws CustomerException;
	Boolean changePassword(StringInputDto stringInputDto) throws CustomerException;
	String getUser() throws CustomerException;
	List<AddressDto> getAddress() throws CustomerException;
	AddressDto addAddress(AddressDto addressDto) throws CustomerException;
	Boolean deleteAcc() throws CustomerException;
	CustomerDto editDetails(CustomerDto customerDto)throws CustomerException;
	AddressDto editAddress(AddressDto addressDto) throws CustomerException;

}