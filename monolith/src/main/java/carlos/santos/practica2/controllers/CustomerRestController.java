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

import carlos.santos.practica2.models.Customer;
import carlos.santos.practica2.services.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/")
	public ResponseEntity<List<Customer>> getCustomers() {
		return new ResponseEntity<>(this.customerService.getCustomers(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(this.customerService.getCustomer(id), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){

		return new ResponseEntity<>(this.customerService.createCustomer(customer), HttpStatus.CREATED);
	}

}