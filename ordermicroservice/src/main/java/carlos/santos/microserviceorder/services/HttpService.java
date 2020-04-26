package carlos.santos.microserviceorder.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.google.gson.Gson;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import carlos.santos.microserviceorder.models.Customer;
import carlos.santos.microserviceorder.models.Product;

@Service
public class HttpService {

	private String HOST = "http://localhost";
	private String MONOLITH_PORT = "8080";
	private String LOGGER_PORT = "8082";

	public Product getProduct(Long id) throws NotFoundException {
		String productString = this.request(HOST + ":" + MONOLITH_PORT + "/products/"+ id, "GET", null);
		Gson gson = new Gson();
		Product p = gson.fromJson(productString,Product.class);
		if (p != null) {
			return p;
		}
		throw new NotFoundException();
	}

	public void updateProduct(Product product) throws NotFoundException {
		Gson gson = new Gson();
		String jsonProduct = gson.toJson(product);
		this.request(HOST + ":" + MONOLITH_PORT + "/products/", "POST", jsonProduct);
	}

	public Customer getCustomer(Long id) throws NotFoundException {
		String customerString = this.request(HOST + ":" + MONOLITH_PORT + "/customers/"+ id, "GET", null);
		Gson gson = new Gson();
		Customer c = gson.fromJson(customerString, Customer.class);
		if (c != null) {
			return c;
		}
		throw new NotFoundException();
	}

	public void updateCustomer(Customer customer) throws NotFoundException {
		Gson gson = new Gson();
		String jsonCustomer = gson.toJson(customer);
		this.request(HOST + ":" + MONOLITH_PORT + "/customers/", "POST", jsonCustomer);
	}

	public void sendMessage(String message){
		this.request(HOST + ":" + LOGGER_PORT + "/logger/", "POST", message);
	}

	public String request(String restUrl, String method, String content) {
		assert restUrl != null;
		assert method == "GET" || method == "POST" && content != null;

		StringBuffer response = new StringBuffer();

		try {
			URL url = new URL(restUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			if (method == "POST") {
				con.setDoOutput(true);
				try(OutputStream os = con.getOutputStream()) {
					byte[] input = content.getBytes("utf-8");
					os.write(input, 0, input.length);
				}
			}

			con.connect();

			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
		return response.toString();

	}

}