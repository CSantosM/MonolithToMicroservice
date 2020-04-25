package carlos.santos.practica2.repositories;

import org.springframework.data.repository.CrudRepository;

import carlos.santos.practica2.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}