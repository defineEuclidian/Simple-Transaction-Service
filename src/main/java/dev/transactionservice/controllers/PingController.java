package dev.transactionservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.transactionservice.models.Ping;

/**
 * PingController class to return a Ping object whenever the /ping path is used.
 * 
 * <p> Consequently, it tests the availability of the service and returns the current server time. </p>
 */
@RestController
public class PingController {

	/**
	 * Method for handling pings.
	 * 
	 * @return Ping object storing the current server time.
	 */
	@GetMapping("/ping")
	public Ping ping() {
		return new Ping();
	}

}
