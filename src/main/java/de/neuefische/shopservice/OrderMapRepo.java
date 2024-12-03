package de.neuefische.shopservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapRepo implements OrderRepo {

    private Map<Integer, Order> orders;

    public OrderMapRepo() {
        this.orders = new HashMap<Integer, Order>();
    }

    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

    public Order getSingle(int id) {
        return orders.get(id);
    }

    public void add(Order order) {
        orders.put(order.id(), order);
    }

    public void remove(Order order) {
        orders.remove(order.id());
   }
}
