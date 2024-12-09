package de.neuefische.shopservice;

import java.util.List;
import java.util.UUID;

public interface OrderRepo {

    List<Order> getAll();

    List<Order> getAllByStatus(OrderStatus status);

    Order getSingle(UUID id);

    void add(Order order);

    void remove(Order order);

}
