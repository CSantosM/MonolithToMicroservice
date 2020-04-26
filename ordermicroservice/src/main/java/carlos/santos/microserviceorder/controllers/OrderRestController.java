package carlos.santos.microserviceorder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carlos.santos.microserviceorder.models.Customer;
import carlos.santos.microserviceorder.models.Order;
import carlos.santos.microserviceorder.models.Product;
import carlos.santos.microserviceorder.services.HttpService;
import carlos.santos.microserviceorder.services.LoggerService;
import carlos.santos.microserviceorder.services.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private HttpService httpService;

	@Autowired
	private LoggerService loggerService;


	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		try {
			Product product = this.httpService.getProduct(order.getProductId());
			Customer customer = this.httpService.getCustomer(order.getCustomerId());

			if (product.hasStock(order.getProductQuantity())) {
				int orderPrice = product.getPrice() * order.getProductQuantity();
				if (customer.canBuyProduct(orderPrice)) {
					product.substractStock(order.getProductQuantity());
					this.httpService.updateProduct(product);
					customer.substractMoney(orderPrice);
					this.httpService.updateCustomer(customer);
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