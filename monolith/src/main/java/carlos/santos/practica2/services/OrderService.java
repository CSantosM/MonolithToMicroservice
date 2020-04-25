package carlos.santos.practica2.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.santos.practica2.models.Order;
import carlos.santos.practica2.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;



	public List<Order> getAllOrders() {
		return (List<Order>) this.orderRepository.findAll();
	}

	public Order createOrder(Order order) {
		return this.orderRepository.save(order);
	}


}