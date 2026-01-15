package org.lab.orderservice.repository;

import org.lab.orderservice.entities.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends CrudRepository<Orders,Long> {

}