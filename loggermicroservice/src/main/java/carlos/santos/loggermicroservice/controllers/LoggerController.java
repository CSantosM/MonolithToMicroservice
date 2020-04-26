package carlos.santos.loggermicroservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carlos.santos.loggermicroservice.services.LoggerService;

@RestController
@RequestMapping("/logger")
public class LoggerController {

	@Autowired
	private LoggerService loggerService;

	@PostMapping("/")
	public ResponseEntity<Void> sendMessage(@RequestBody String message) {
		this.loggerService.sendMessage(message);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
