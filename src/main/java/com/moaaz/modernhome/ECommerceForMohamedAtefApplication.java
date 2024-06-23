package com.moaaz.modernhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ECommerceForMohamedAtefApplication {

    private static String SERVER_PORT = "9090";

    public static void main(String[] args) {
        SpringApplication.run(ECommerceForMohamedAtefApplication.class, args);
        System.out.println("Running Successfully On Port" + SERVER_PORT);
        System.out.println("This is The Second Version of My E-commerce For Modern Home!");
        System.out.println("Done By Moaaz Suliman As A Backend Developer \n Mohamed Suliman , Mohamed Tamer As A Frontend Developer");
        System.out.println("Admin Dashboard Are Finished...");
    }

}
