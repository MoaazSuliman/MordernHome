package com.moaaz.modernhome.Product;

import com.moaaz.modernhome.Category.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProductResponse {
    private long id;
    private String name;
    private String details;
    private List<String> images;
    private double price;
    private double discount;

    private double total;

    private LocalDate creationDate;

    private String categoryName;


    public static ProductResponse convertProductToProductResponse(Product converterProduct) {
        // check if this doesn't have a category to create a default category for him.


        return ProductResponse
                .builder()
                .id(converterProduct.getId())
                .name(converterProduct.getName())
                .details(converterProduct.getDetails())
                .price(converterProduct.getPrice())
                .discount(converterProduct.getDiscount())
                .total(converterProduct.getTotal())
                .images(converterProduct.getImages())
                .categoryName(converterProduct.getCategory().getName())
                .creationDate(converterProduct.getCreationDate())
                .build();
    }
}
