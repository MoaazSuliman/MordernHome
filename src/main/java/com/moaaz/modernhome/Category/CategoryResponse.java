package com.moaaz.modernhome.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private long id;
    private String name;
    private String details;
    private LocalDate creationDate;

    private long numberOfProducts;
}
