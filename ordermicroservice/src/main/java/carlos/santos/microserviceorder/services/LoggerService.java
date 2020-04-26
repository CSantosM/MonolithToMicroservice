package carlos.santos.microserviceorder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

	@Value("${external.logger}")
	private boolean externalLogger;

	@Autowired
	private HttpService httpService;

	public void sendMessage(String message) {

		if (this.externalLogger) {

			this.httpService.sendMessage(message);

			return;
		}
		System.out.println(message);
	}
}