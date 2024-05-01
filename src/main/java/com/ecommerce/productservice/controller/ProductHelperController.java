package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.entity.Images;
import com.ecommerce.productservice.repository.PincodeRepo;
import com.ecommerce.productservice.service.declaration.HelperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/helper")
public class ProductHelperController {

    @Autowired
    HelperService helperService;
    @Autowired
    PincodeRepo pincodeRepo;

    @Operation(summary = "Returns modified image URLs with new height,width & quality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modified Image URLs",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Images.class)) })
    })
    @GetMapping("/image-resize")
    public ResponseEntity<Map<String, String>> imageSize(@RequestBody Map<String, String> images,
                                                         @RequestParam int newHeight,
                                                         @RequestParam int newQuality,
                                                         @RequestParam int newWidth) {

        return new ResponseEntity<>(helperService.imageResizer(images, newHeight, newQuality, newWidth), HttpStatus.OK);
    }

    @Operation(summary = "Returns number of days to deliver to specified Pincode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Days to deliver the specific Size of that product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) })
    })
    @GetMapping("/isDeliverable")
    public ResponseEntity<String> pincode(@RequestParam String pincode,
                                          @RequestParam String sizeId) {
       return new ResponseEntity<>(helperService.getDistance(pincode,sizeId),HttpStatus.OK);
    }


}

