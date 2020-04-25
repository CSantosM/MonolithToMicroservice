package carlos.santos.practica2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	int money;

	public Customer() {
	}

	public Customer(int money) {
		this.money = money;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", money=" + money + "]";
	}

	public boolean canBuyProduct(int price) {
		return this.money >= price;
	}

	public void substractMoney(int quantity) {
		this.money -= quantity;

	}

}