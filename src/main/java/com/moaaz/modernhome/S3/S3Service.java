package com.moaaz.modernhome.S3;

import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
public class S3Service {


    @Autowired
    private  S3Client s3Client;



    // will take image and upload it to s3 then return http url
    public String uploadImageToS3AndGetImageUrl(String base64Image) {

//        log.info("Trying TO Update Image With Base "+base64Image);
        // Decode the Base64 image
        base64Image =  base64Image.replaceFirst("data:[^;]*;base64,", "");
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Generate a unique filename or key for the image
        String filename = UUID.randomUUID().toString() + ".png";

        // Upload the image to S3 bucket
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("modern-home")
                .key(filename)
                .build();
        s3Client.putObject(request, RequestBody.fromBytes(imageBytes));

        // Return the URL of the uploaded image
        return "https://modern-home.s3.amazonaws.com/" + filename;

    }


    public void deleteImageFromS3(String imageUrl){

        AmazonS3 s3Client = AmazonS3Client.builder()
                .withRegion(Regions.EU_NORTH_1) // Change to your desired region
                .withCredentials(new SystemPropertiesCredentialsProvider())
                .build();

        String bucketName = "modern-home";


        s3Client.deleteObject(bucketName, imageUrl);
    }

}
