package carlos.santos.practica2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ORDERS")// H2 has 'order' as a keyword
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;

	Long customerId;
	Long productId;
	int productQuantity;

	public Order() {
	}

	public Order(Long customerId, Long productId, int productQuantity) {
		this.customerId = customerId;
		this.productId = productId;
		this.productQuantity = productQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "Order [customerId=" + customerId + ", id=" + id + ", productId=" + productId + ", productQuantity="
				+ productQuantity + "]";
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

}