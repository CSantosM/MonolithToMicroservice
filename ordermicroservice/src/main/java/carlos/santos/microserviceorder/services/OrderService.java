package carlos.santos.microserviceorder.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.santos.microserviceorder.models.Order;
import carlos.santos.microserviceorder.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public Order createOrder(Order order) {
		return this.orderRepository.save(order);
	}


}
