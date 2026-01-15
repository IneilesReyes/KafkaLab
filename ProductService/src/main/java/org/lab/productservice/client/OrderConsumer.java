package org.lab.productservice.client;

import org.lab.productservice.entities.Product;
import org.lab.productservice.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @Autowired
    private IProductRepository productRepository;

    @KafkaListener(topics = "order-events-2", groupId = "stock-group")
    public void consumeOrder(String message) {
        //sent msg from orderservice String message = savedOrders.getIdOrder() + "," + savedOrders.getIdProduct() + "," + savedOrders.getQuantity();
        String[] parts = message.split(",");
        String productId = parts[1];//count from 0
        int quantity = Integer.parseInt(parts[2]);

        Product product = productRepository.findById(Long.valueOf(productId)).orElse(null);
        if (product != null){
            int initialStock = product.getStock();
            product.setStock(product.getStock() - quantity);
            Product updatedProduct = productRepository.save(product);
            System.out.println("Update of the product:  " + updatedProduct.getName() + " from " + initialStock + " to: " + updatedProduct.getStock());
        }
    }
}