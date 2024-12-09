package de.neuefische.shopservice;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderRepo {

    List<Order> getAll();

    List<Order> getAllByStatus(OrderStatus status);

    Map<OrderStatus, Order> getOldestOrderPerStatus();

    Order getSingle(UUID id);

    void add(Order order);

    void remove(Order order);

}
