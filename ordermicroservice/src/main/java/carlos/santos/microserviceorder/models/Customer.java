package carlos.santos.microserviceorder.models;

public class Customer {

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