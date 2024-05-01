package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.entity.PincodeDetails;
import com.ecommerce.productservice.entity.SizeVariantDetails;
import com.ecommerce.productservice.repository.PincodeRepo;
import com.ecommerce.productservice.repository.SkuRepo;
import com.ecommerce.productservice.service.declaration.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class HelperServiceImpl implements HelperService {

    @Autowired
    PincodeRepo pincodeRepo;
    @Autowired
    SkuRepo skuRepo;

    private static final double EARTH_RADIUS_KM = 6371;

    @Override
    public Map<String, String> imageResizer(Map<String, String> image, int newHeight, int newQuality, int newWidth) {

        Map<String, String> responseImages = new LinkedHashMap<>();
        image.forEach((key, value) -> {
            if(value!=null)
                responseImages.put(key, modifyURL(value, newHeight, newQuality, newWidth));
        });
        return responseImages;
    }

    @Override
    public String getDistance(String pincode,String sizeId) {
        SizeVariantDetails svd = skuRepo.findSize(sizeId).getSizeVariantDetails().getFirst();
        AtomicBoolean flag = new AtomicBoolean(false);
        if(svd != null && svd.getQuantity()>0){
            List<String> codes = List.of(svd.getAvailablePincodes().split(","));
            codes.forEach(s -> {
                if (pincode.startsWith(s))
                    flag.set(true);
            } );
        }
        if(flag.get()) {
            PincodeDetails to = pincodeRepo.findById("700018").get();
            PincodeDetails from = pincodeRepo.findById(pincode).get();
            double distance = distance(to.getLatitude(), to.getLongitude(), from.getLatitude(), from.getLongitude());
            return Integer.toString(Math.round((float) distance/300)+1);
        }else {
            return "Product not available at your pincode";
        }

    }

    public void calculateDistance(String pincode){
        PincodeDetails to = pincodeRepo.findById("700018").get();
        PincodeDetails from = pincodeRepo.findById(pincode).get();
        double dis = distance(to.getLatitude(), to.getLongitude(), from.getLatitude(), from.getLongitude());

    }

    public static double degreesToRadians(double degrees) {
        return degrees * (Math.PI / 180);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = degreesToRadians(lat1);
        double lat2Rad = degreesToRadians(lat2);
        double lon1Rad = degreesToRadians(lon1);
        double lon2Rad = degreesToRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance =Math.round(EARTH_RADIUS_KM * c);
        return distance + distance*((double) 20 /100);
    }

    public String modifyURL(String originalURL, int newHeight, int newQuality, int newWidth) {
        // Regular expression to find and replace height, quality, and width
        String regex = "h_\\d+,\\s*q_\\d+,\\s*w_\\d+";

        // Replacement string with dynamic values
        String replacement = String.format("h_%d,q_%d,w_%d", newHeight, newQuality, newWidth);

        // Replace the matched pattern with the replacement
        return originalURL.replaceAll(regex, replacement);
    }
}
