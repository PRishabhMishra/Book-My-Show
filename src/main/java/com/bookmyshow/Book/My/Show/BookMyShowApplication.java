package com.bookmyshow.Book.My.Show;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title ="Book My Show Swagger UI",version = "1.0",description = "This swagger page contains all the endpoints details of Book My Show Application",contact = @Contact(name = "Rishabh",email = "mishramanu098@gmail.com",url="https://github.com/PRishabhMishra")))
@SpringBootApplication
public class BookMyShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

}
