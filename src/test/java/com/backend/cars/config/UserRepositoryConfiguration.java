package com.backend.cars.config;


import com.backend.cars.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class UserRepositoryConfiguration {
    @Bean
    @Primary
    public UserRepository userRepository(){
        return Mockito.mock(UserRepository.class);
    }
}
