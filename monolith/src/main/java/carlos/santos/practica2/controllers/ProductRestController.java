package carlos.santos.practica2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carlos.santos.practica2.models.Product;
import carlos.santos.practica2.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductRestController {

	@Autowired
	private ProductService productService;


	@GetMapping("/")
	public ResponseEntity<List<Product>> getProducts(){

		return new ResponseEntity<>( this.productService.getProducts(), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(this.productService.getProduct(id), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody Product product){

		return new ResponseEntity<>(this.productService.createProduct(product), HttpStatus.CREATED);
	}

}