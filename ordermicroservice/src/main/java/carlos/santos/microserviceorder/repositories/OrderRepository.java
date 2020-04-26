package carlos.santos.microserviceorder.repositories;


import org.springframework.data.repository.CrudRepository;

import carlos.santos.microserviceorder.models.Order;


public interface OrderRepository extends CrudRepository<Order, Long>{

}