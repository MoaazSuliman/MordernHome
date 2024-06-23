package com.moaaz.modernhome.Navbar;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NavbarRequest {

    @NotNull
    @NotEmpty
    private String imageBase64;
}
