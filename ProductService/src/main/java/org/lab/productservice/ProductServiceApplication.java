package org.lab.productservice;

import org.lab.productservice.entities.Product;
import org.lab.productservice.repository.IProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
        @Bean
        CommandLineRunner initProducts(IProductRepository productRepository) {
            return args -> {
                productRepository.save(new Product( 1L, "Laptop", 100));
                productRepository.save(new Product( 2L ,"Phone", 200));
                productRepository.save(new Product( 3L,"Headphones", 300));

                System.out.println("Products initialized in  database!");
            };
        }
}
