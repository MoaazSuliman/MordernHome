package com.moaaz.modernhome.Navbar;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.transform.sax.SAXTransformerFactory;

@Entity
@Table(name = "navbar_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NavbarImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String imageUrl;

}
