package org.lab.productservice.client;

import jakarta.transaction.Transactional;
import org.lab.productservice.configuration.OrderEvent;
import org.lab.productservice.entities.Product;
import org.lab.productservice.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    @Autowired
    private IProductRepository productRepository;

    @Transactional
    @KafkaListener(topics = "order-events-2", groupId = "stock-group")
    public void consumeOrder(OrderEvent event) {

        Product product = productRepository.findById(event.getProductId()).orElse(null);
        if (product == null) {
            System.out.println("Product with id " + event.getProductId() + " not found!");
            return;
        }

        int oldStock = product.getStock();
        product.setStock(oldStock - event.getQuantity());
        Product updatedProduct = productRepository.save(product);

        System.out.println("Updated product " + updatedProduct.getName() +
                " from " + oldStock + " to " + updatedProduct.getStock());
    }
}
