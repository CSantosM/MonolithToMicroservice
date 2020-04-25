package carlos.santos.practica2.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import carlos.santos.practica2.models.Customer;
import carlos.santos.practica2.repositories.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@PostConstruct
	private void initialize() {
		this.customerRepository.save(new Customer(200));
		this.customerRepository.save(new Customer(10));
	}

	public boolean hasMoney(Long customerId, int orderPrice) throws NotFoundException {
		return this.getCustomer(customerId).canBuyProduct(orderPrice);

	}

	public void substrackMoney(Long customerId, int orderPrice) throws NotFoundException {
		Customer customer = this.getCustomer(customerId);
		customer.substractMoney(orderPrice);
		this.customerRepository.save(customer);

	}

	public Customer getCustomer(Long id) throws NotFoundException {
		return this.customerRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	public List<Customer> getCustomers() {
		return (List<Customer>) this.customerRepository.findAll();
	}

	public Customer createCustomer(Customer customer) {
		return this.customerRepository.save(customer);
	}

}