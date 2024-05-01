package com.ecommerce.productservice.service.declaration;

import java.util.Map;

public interface HelperService {

    Map<String,String> imageResizer(Map<String,String> image,int newHeight, int newQuality, int newWidth);

    String getDeliveryAvailability(String pincode, String sizeId);
}
