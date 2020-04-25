package carlos.santos.practica2.services;

import org.springframework.stereotype.Service;

@Service
public class LoggerService {

	public void sendMessage(String message){
		System.out.println(message);
	}

}