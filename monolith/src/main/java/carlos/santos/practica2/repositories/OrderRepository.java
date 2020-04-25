package carlos.santos.practica2.repositories;


import org.springframework.data.repository.CrudRepository;

import carlos.santos.practica2.models.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}