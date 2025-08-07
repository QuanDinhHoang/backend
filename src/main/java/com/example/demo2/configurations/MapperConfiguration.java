package com.example.demo2.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Khoi tao ModelMapper vi ModelMapper ko the tu dong tao nhu Repository
@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
