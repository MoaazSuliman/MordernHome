package com.moaaz.modernhome.Product;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class ProductRequest {


    private String name;

    private String details;


    private List<String> images;
    private double price;

    @Builder.Default
    private double discount=0.0;

    private long categoryId;
}
