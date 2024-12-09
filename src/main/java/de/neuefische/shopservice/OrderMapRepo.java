package de.neuefische.shopservice;

import java.util.*;
import java.util.stream.Collectors;

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

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        return orders.values().stream()
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
