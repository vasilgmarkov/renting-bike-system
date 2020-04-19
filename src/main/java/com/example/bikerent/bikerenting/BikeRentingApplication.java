package com.example.bikerent.bikerenting;


import com.example.bikerent.bikerenting.domain.Bike;
import com.example.bikerent.bikerenting.domain.Rental;
import com.example.bikerent.bikerenting.domain.Store;
import com.example.bikerent.bikerenting.domain.User;
import com.example.bikerent.bikerenting.repository.BikeRepository;
import com.example.bikerent.bikerenting.repository.RentalRepository;
import com.example.bikerent.bikerenting.repository.StoreRepository;
import com.example.bikerent.bikerenting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;


@SpringBootApplication
public class  BikeRentingApplication{



	public static void main(String[] args) {
		SpringApplication.run(BikeRentingApplication.class, args);



	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(BikeRepository bikeRepository, StoreRepository storeRepository, UserRepository userRepository, RentalRepository rentalRepository){
		return (args) -> {






			//Store
			Store store = new Store();
			storeRepository.save(store);
			//Bikes
			Bike bike1 = new Bike("Normal", 10.00, "https://www.biciescapa.com/73378-tm_large_default/attain-race-cube-bicicleta-de-carretera-2019-21202.jpg");
			bike1.setStore(store);
			bikeRepository.save(bike1);

			Bike bike2 = new Bike("Normal", 10.00, "https://www.biciescapa.com/50155-tm_large_default/tarmac-sl6-expert-mujer-specialized-bicicleta-de-carretera-2018-16411.jpg");
			bike2.setStore(store);
			bikeRepository.save(bike2);

			Bike bike3 = new Bike("Normal", 10.00,"https://www.biciescapa.com/73774-tm_large_default/bicicleta-chopper-bixby-1-sp-blk-sand-00-2018-21441.jpg");
			bike3.setStore(store);
			bikeRepository.save(bike3);

			Bike bike4 = new Bike("Mountain", 12.00,"https://www.biciescapa.com/68959-tm_large_default/oiz-m50-2019-29-orbea-bicicleta-de-montana-2019-20356.jpg");
			bike4.setStore(store);
			bikeRepository.save(bike4);

			Bike bike5 = new Bike("Mountain", 12.00,"https://www.biciescapa.com/84676-tm_large_default/bicicleta-orbea-oiz-27-m30-2020-23000.jpg");
			bike5.setStore(store);
			bikeRepository.save(bike5);

			Bike bike6 = new Bike("Mountain", 12.00,"https://mbici.es/app/uploads/2019/07/plantilla-principal-marlin5-3-1024x622.jpg");
			bike6.setStore(store);
			bikeRepository.save(bike6);





		};
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD",
				"GET", "POST", "PUT", "DELETE", "PATCH"));
		// setAllowCredentials(true) is important, otherwise:
		// will fail with 403 Invalid CORS request
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}


}
