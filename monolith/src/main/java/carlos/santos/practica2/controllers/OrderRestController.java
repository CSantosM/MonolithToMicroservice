package carlos.santos.practica2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carlos.santos.practica2.models.Order;
import carlos.santos.practica2.services.CustomerService;
import carlos.santos.practica2.services.LoggerService;
import carlos.santos.practica2.services.OrderService;
import carlos.santos.practica2.services.ProductService;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private LoggerService loggerService;

	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {

		try {
			if (this.productService.hasStock(order.getProductId(), order.getProductQuantity())) {
				int orderPrice = this.productService.getPrice(order.getProductId()) * order.getProductQuantity();
				if (this.customerService.hasMoney(order.getCustomerId(), orderPrice)) {
					this.productService.substrackStock(order.getProductId(), order.getProductQuantity());
					this.customerService.substrackMoney(order.getCustomerId(), orderPrice);
					Order o = this.orderService.createOrder(order);
					this.loggerService.sendMessage("Gracias por su compra");
					return new ResponseEntity<>(o, HttpStatus.CREATED);
				}
				this.loggerService.sendMessage("Dinero insuficiente. Predido rechazado");
				return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (NotFoundException e) {
			this.loggerService.sendMessage("Producto o cliente incorrecto. Predido rechazado");
			return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		this.loggerService.sendMessage("Stock insuficiente. Predido rechazado");
		return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}



}