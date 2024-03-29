package com.moaaz.modernhome.Product;

import com.moaaz.modernhome.Category.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.util.Lazy;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String details;

    private List<String> images;
    private double price;
    private double discount;

    private double total;

    private LocalDate creationDate;

    private boolean isDeleted;

    //    @ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE  , CascadeType.PERSIST , CascadeType.REFRESH})
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
