package com.example.iotservice;

import com.example.iotservice.dtos.*;
import com.example.iotservice.models.House;
import com.example.iotservice.models.User;
import com.example.iotservice.services.DeviceService;
import com.example.iotservice.services.HubService;
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
	public CommandLineRunner loadData(UserService userService, HouseService houseService, HubService hubService,
									  DeviceService deviceService) {
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
			House savedHouse1 = modelMapper().map(houseService.createHouse(house1), House.class);

			AddHouseDTO house2 = new AddHouseDTO();
			house2.setAddress("456 Maple St");
			house2.setUser(modelMapper().map(userService.getUserByEmail("jane.smith@example.com"), User.class));
			House savedHouse2 = modelMapper().map(houseService.createHouse(house2), House.class);

			AddHubDTO hub1 = new AddHubDTO();
			hub1.setName("Hub 1");
			hub1.setHouse(savedHouse1);
			HubDTO savedHub1 = hubService.createHub(hub1);

			AddHubDTO hub2 = new AddHubDTO();
			hub2.setName("Hub 2");
			hub2.setHouse(savedHouse2);
			HubDTO savedHub2 = hubService.createHub(hub2);

			AddDeviceDTO device1 = new AddDeviceDTO();
			device1.setName("Device 1");
			device1.setHub(savedHub1);
			deviceService.createDevice(device1);

			AddDeviceDTO device2 = new AddDeviceDTO();
			device2.setName("Device 2");
			device2.setHub(savedHub1);
			deviceService.createDevice(device2);

			AddDeviceDTO device3 = new AddDeviceDTO();
			device3.setName("Device 3");
			device3.setHub(savedHub2);
			deviceService.createDevice(device3);

			AddDeviceDTO device4 = new AddDeviceDTO();
			device4.setName("Device 4");
			device4.setHub(savedHub2);
			deviceService.createDevice(device4);

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
