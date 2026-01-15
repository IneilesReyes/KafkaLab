package org.lab.orderservice.controller;

import org.lab.orderservice.entities.Orders;
import org.lab.orderservice.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order-events-2";

    @PostMapping("/addOrder")
    public Orders createOrder(@RequestBody Orders orders) {
        orders.setStatus("CREATED");
        Orders savedOrders = orderRepository.save(orders);
        // send orders to Kafka
        String message = savedOrders.getIdOrder() + "," + savedOrders.getIdProduct() + "," + savedOrders.getQuantity();
        kafkaTemplate.send(TOPIC, message);

        return savedOrders;
    }
}