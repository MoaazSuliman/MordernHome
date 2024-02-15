package com.moaaz.modernhome.Navbar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

@RestController
@RequestMapping("/navbarImages")
@CrossOrigin("*")
public class NavbarImageController {


    @Autowired
    private NavbarImageService navbarImageService;

    @PostMapping
    public ResponseEntity<?> putNavbarImages(@RequestBody List<String> imagesBase64) {
        return new ResponseEntity<>(navbarImageService.putImages(imagesBase64), HttpStatus.ACCEPTED);
    }


    @GetMapping
    public ResponseEntity<?> getAllNavbarImages() {
        return new ResponseEntity<>(navbarImageService.getAllNavbarImages(), HttpStatus.OK);
    }
}
