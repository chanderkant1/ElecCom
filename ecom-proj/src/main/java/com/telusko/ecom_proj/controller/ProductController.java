package com.telusko.ecom_proj.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telusko.ecom_proj.model.Product;
import com.telusko.ecom_proj.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@RequestMapping("/")
	public String greet() {
		return "hello";
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> li =  service.getAllProducts();

			return new ResponseEntity<List<Product>>(li,HttpStatus.OK);
	}
	
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getById(@PathVariable int id){
		Product p = service.findById(id);
		if(p != null) {
			return new ResponseEntity<Product>(p,HttpStatus.OK);
		}

		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}
	
	
	@PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
        try {
            System.out.println(product);
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/product/{productId}/image")
	public ResponseEntity<byte[]> getProductImage(@PathVariable int productId){
		
		Product p = service.findById(productId);
		byte[] imgFile = p.getImageDate();
		
		return ResponseEntity.ok().contentType(MediaType.valueOf(p.getImageType())).body(imgFile);
	}
	
	 @PutMapping("/product/{id}")
	    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
	                                                @RequestPart MultipartFile imageFile){
	        Product product1 = null;
	        try {
	            product1 = service.updateProduct(id, product, imageFile);
	        } catch (IOException e) {
	            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
	        }
	        if(product1 != null)
	            return new ResponseEntity<>("Updated", HttpStatus.OK);
	        else
	            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
	    }

	
	 @DeleteMapping("/product/{id}")
	    public ResponseEntity<String> deleteProduct(@PathVariable int id){
	        Product product = service.findById(id);
	        if(product != null) {
	            service.deleteProduct(id);
	            return new ResponseEntity<>("Deleted", HttpStatus.OK);
	        }
	        else
	            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);

	    }
	
	 @GetMapping("/products/search")
	    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
	        List<Product> products = service.searchProducts(keyword);
	        System.out.println("searching with " + keyword);
	        return new ResponseEntity<>(products, HttpStatus.OK);
	    }
	
}
