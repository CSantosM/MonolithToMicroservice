package carlos.santos.practica2.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import carlos.santos.practica2.models.Product;
import carlos.santos.practica2.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@PostConstruct
	private void initialize() {
		this.productRepository.save(new Product(5, "Mascarillas", 10));
		this.productRepository.save(new Product(1, "Cerveza", 2));
	}

	public boolean hasStock(Long productId, int productQuantity) throws NotFoundException {
		return this.getProduct(productId).hasStock(productQuantity);
	}

	public int getPrice(Long productId) throws NotFoundException {
		return this.getProduct(productId).getPrice();

	}

	public void substrackStock(Long productId, int productQuantity) throws NotFoundException {
		Product product = this.getProduct(productId);
		product.substractStock(productQuantity);
		this.productRepository.save(product);

	}

	public List<Product> getProducts() {
		return (List<Product>) this.productRepository.findAll();
	}

	public Product getProduct(Long id) throws NotFoundException {
		return this.productRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	public Product createProduct(Product product) {
		return this.productRepository.save(product);
	}

}