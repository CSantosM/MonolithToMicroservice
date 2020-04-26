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

	// Monolith URL
	private String host = "http://localhost:8080/";

	public Product getProduct(Long id) throws NotFoundException {
		String productString = this.request("products/"+ id, "GET", null);
		Gson gson = new Gson();
		return gson.fromJson(productString,Product.class);
	}

	public void updateProduct(Product product) throws NotFoundException {
		Gson gson = new Gson();
		String jsonProduct = gson.toJson(product);
		this.request("products/", "POST", jsonProduct);
	}

	public Customer getCustomer(Long id) throws NotFoundException {
		String customerString = this.request("customers/"+ id, "GET", null);
		Gson gson = new Gson();
		return gson.fromJson(customerString, Customer.class);
	}

	public void updateCustomer(Customer customer) throws NotFoundException {
		Gson gson = new Gson();
		String jsonCustomer = gson.toJson(customer);
		this.request("customers/", "POST", jsonCustomer);
	}

	public String request(String path, String method, String content) throws NotFoundException {
		assert path != null;
		assert method == "GET" || method == "POST" && content != null;

		try {
			URL url = new URL(this.host + path);
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
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
			return response.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
		throw new NotFoundException();
	}

}