package com.example.iotservice;

import com.example.iotservice.dtos.AddHouseDTO;
import com.example.iotservice.dtos.AddUserDTO;
import com.example.iotservice.models.House;
import com.example.iotservice.models.User;
import com.example.iotservice.services.UserService;
import com.example.iotservice.services.HouseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IoTServiceApplication {

	@Bean
	public CommandLineRunner loadData(UserService userService, HouseService houseService) {
		return args -> {
			AddUserDTO user1 = new AddUserDTO();
			user1.setName("John Doe");
			user1.setEmail("john.doe@example.com");
			userService.createUser(user1);

			AddUserDTO user2 = new AddUserDTO();
			user2.setName("Jane Smith");
			user2.setEmail("jane.smith@example.com");
			userService.createUser(user2);

			AddHouseDTO house1 = new AddHouseDTO();
			house1.setAddress("123 Oak St");
			house1.setUser(modelMapper().map(userService.getUserByEmail("john.doe@example.com"), User.class));
			houseService.createHouse(house1);

			AddHouseDTO house2 = new AddHouseDTO();
			house2.setAddress("456 Maple St");
			house2.setUser(modelMapper().map(userService.getUserByEmail("jane.smith@example.com"), User.class));
			houseService.createHouse(house2);
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
