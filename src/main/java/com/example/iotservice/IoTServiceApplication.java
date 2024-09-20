package com.example.iotservice;

import com.example.iotservice.dtos.AddUserDTO;
import com.example.iotservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IoTServiceApplication {

	@Bean
	public CommandLineRunner loadData(UserService userService) {
		return args -> {
			AddUserDTO user1 = new AddUserDTO();
			user1.setName("John Doe");
			user1.setEmail("john.doe@example.com");
			userService.createUser(user1);

			AddUserDTO user2 = new AddUserDTO();
			user2.setName("Jane Smith");
			user2.setEmail("jane.smith@example.com");
			userService.createUser(user2);
		};
	}
	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

		return modelMapper;
	}
	public static void main(String[] args) {
		SpringApplication.run(IoTServiceApplication.class, args);
	}

}
