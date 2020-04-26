package carlos.santos.loggermicroservice.services;

import org.springframework.stereotype.Service;

@Service
public class LoggerService {

	public void sendMessage(String message){
		System.out.println(message);
	}
}