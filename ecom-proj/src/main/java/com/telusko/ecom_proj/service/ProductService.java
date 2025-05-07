package com.telusko.ecom_proj.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telusko.ecom_proj.model.Product;
import com.telusko.ecom_proj.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo repo;

	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return repo.findAll();
//		return null;
	}

	public Product findById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	public Product addProduct(Product product, MultipartFile imgFile) throws IOException
	{
		// TODO Auto-generated method stub
		product.setImageName(imgFile.getOriginalFilename());
		product.setImageType(imgFile.getContentType());
		product.setImageDate(imgFile.getBytes());
		return repo.save(product);
	}

	 public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
	        product.setImageDate(imageFile.getBytes());
	        product.setImageName(imageFile.getOriginalFilename());
	        product.setImageType(imageFile.getContentType());
	        return repo.save(product);
	    }

	 public void deleteProduct(int id) {
	        repo.deleteById(id);
	    }

	 public List<Product> searchProducts(String keyword) {
	        return repo.searchProducts(keyword);
	    }
	
	
	
}
