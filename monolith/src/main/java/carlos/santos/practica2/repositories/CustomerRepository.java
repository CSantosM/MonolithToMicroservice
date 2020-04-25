package carlos.santos.practica2.repositories;

import org.springframework.data.repository.CrudRepository;

import carlos.santos.practica2.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}