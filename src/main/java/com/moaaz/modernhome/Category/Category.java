package com.moaaz.modernhome.Category;

import com.moaaz.modernhome.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String details;
    private LocalDate creationDate;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return this.products.stream().filter(product -> !product.isDeleted()).toList();
    }

}
