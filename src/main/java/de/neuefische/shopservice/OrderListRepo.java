package de.neuefische.shopservice;

import java.util.ArrayList;
import java.util.List;

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

    public void add(Order order) {
        this.orders.add(order);
    }

    public void remove(Order order) {
        this.orders.remove(order);
    }

    public Order getSingle(int id) {
        return this.orders.stream()
                .filter(order -> id == order.id())
                .findFirst()
                .orElse(null);
    }
}
