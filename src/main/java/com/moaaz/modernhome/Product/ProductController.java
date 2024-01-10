package com.moaaz.modernhome.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/moaaz/api/modernhome/products")
@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.addProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable long productId) {
        return new ResponseEntity<>(productService.updateProduct(productRequest, productId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(productService.getAll()
                , HttpStatus.OK);
    }

    @GetMapping("/getById/{productId}")
    public ResponseEntity<?>getById(@PathVariable long productId){
        return new ResponseEntity<>(productService.getProductResponseById(productId) , HttpStatus.OK);
    }
    @GetMapping("/search/{text}")
    public ResponseEntity<?> searchByText(@PathVariable String text) {
        return new ResponseEntity<>(productService.search(text), HttpStatus.OK);
    }


    @GetMapping("/getAllProducts/{categoryId}")
    public ResponseEntity<?> getAllProductsForCategory(@PathVariable long categoryId) {
        return new ResponseEntity<>(productService.getAllByCategoryId(categoryId), HttpStatus.OK);
    }
}
