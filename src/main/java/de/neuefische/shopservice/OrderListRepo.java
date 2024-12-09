package de.neuefische.shopservice;

import java.util.*;
import java.util.stream.Collectors;

public class OrderListRepo implements OrderRepo {
    private final List<Order> orders;
    
    public OrderListRepo() {
        this.orders = new ArrayList<Order>();
    }

    public OrderListRepo(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getAll() {
        return orders;
    }

    public List<Order> getAllByStatus(OrderStatus status) {
        return orders
                .stream()
                .filter(order -> order.status().equals(status))
                .toList();
    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::status,
                        Collectors.minBy(Comparator.comparing(Order::timestamp))
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get()
                ));
    }

    public void add(Order order) {
        this.orders.add(order);
    }

    public void remove(Order order) {
        this.orders.remove(order);
    }

    public Order getSingle(UUID id) {
        return this.orders.stream()
                .filter(order -> id.equals(order.id()))
                .findFirst()
                .orElse(null);
    }
}
