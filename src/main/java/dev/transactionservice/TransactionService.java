package dev.transactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TransactionApplication class to start the transaction service itself.
 */
@SpringBootApplication
public class TransactionService {

	/**
	 * The main function for running the transaction service.
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {
		SpringApplication.run(TransactionService.class, args);
	}

}
