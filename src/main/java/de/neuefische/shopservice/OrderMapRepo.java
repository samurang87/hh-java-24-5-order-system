package de.neuefische.shopservice;

import java.util.*;

public class OrderMapRepo implements OrderRepo {

    private Map<UUID, Order> orders;

    public OrderMapRepo() {
        this.orders = new HashMap<UUID, Order>();
    }

    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

    public List<Order> getAllByStatus(OrderStatus status) {
        return orders.values()
                .stream()
                .filter(order -> order.status().equals(status))
                .toList();
    }

    public Order getSingle(UUID id) {
        return orders.get(id);
    }

    public void add(Order order) {
        orders.put(order.id(), order);
    }

    public void remove(Order order) {
        orders.remove(order.id());
   }
}
