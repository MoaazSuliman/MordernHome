package com.moaaz.modernhome.Navbar;

import com.moaaz.modernhome.S3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavbarImageService {


    @Autowired
    private NavbarRepository navbarRepository;
    @Autowired
    private S3Service s3Service;

    public List<String> putImages(List<String> imagesBase64) {
        // 1- get all images for navbar from the database
        List<NavbarImage> navbarImages = navbarRepository.findAll();
        // 2- delete all the images from the s3  server
//        navbarImages.forEach(navbarImage -> {
//                    s3Service.deleteImageFromS3(navbarImage.getImageUrl());
//                }
//        );

        // 3- delete the image that are in the database before saving the new images
        navbarRepository.deleteAll();
        // 4- upload this images to s3 service
        List<NavbarImage> newNavbarImages = imagesBase64.stream().map(imageBase64
                -> new NavbarImage(0, uploadImage(imageBase64))).toList();

        // 5- save these images to the database

        return navbarRepository.saveAll(newNavbarImages).stream().map(navbarImage -> new String(navbarImage.getImageUrl())).toList();

    }

    public List<String> getAllNavbarImages() {
        return navbarRepository.findAll().stream().map(navbarImage -> navbarImage.getImageUrl()).toList();
    }


    public String uploadImage(String image) {
        return (!image.startsWith("https")) ? s3Service.uploadImageToS3AndGetImageUrl(image) : image;
    }


}
